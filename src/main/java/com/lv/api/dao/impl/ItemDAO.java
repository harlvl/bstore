package com.lv.api.dao.impl;

import com.lv.api.dao.impl.tables.ItemColumns;
import com.lv.api.dao.impl.tables.ItemsProvidersColumns;
import com.lv.api.dto.ItemDTO;
import com.lv.api.dto.ItemsProviderDTO;
import com.lv.api.model.Item;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class ItemDAO implements IItemDAO {
    private final DataSource dataSource;

    @SuppressWarnings("MnInjectionPoints")
    public ItemDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "SELECT i.id, i.name, i.quantity FROM item i WHERE i.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToItem(rs));
            }

        } catch (SQLException e) {
            handleSQLError(String.format("Error fetching item with id %d", id), e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ItemDTO> save(ItemDTO dto) {
        String sql = "INSERT INTO item (name, quantity) VALUES (?, ?) RETURNING id, name, quantity";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int i = 0;
            stmt.setString(++i, dto.getName());
            stmt.setInt(++i, dto.getQuantity());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToDTO(rs));
            }
        } catch (SQLException e) {
            handleSQLError(String.format("Error saving item with id: %s", dto.getId()), e);
        }
        return Optional.empty();
    }

    @Override
    public List<ItemsProviderDTO> findItemsProvider(Long itemId) {

        String sql = "select p.id, p.name, p.phone_number, ip.cost from provider p " +
                "join items_providers ip on p.id = ip.provider_id " +
                "join item i on ip.item_id = i.id " +
                "where i.id = ? order by ip.cost asc, p.name asc";

        List<ItemsProviderDTO> out = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setLong(1, itemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Optional<ItemsProviderDTO> dto = Optional.of(mapRowToItemProviders(rs));
                dto.ifPresent(out::add);
            }
        } catch (SQLException e) {
            handleSQLError(String.format("Error fetching item providers for id: %s", itemId), e);
        }

        return out;
    }

    private ItemsProviderDTO mapRowToItemProviders(ResultSet rs) throws SQLException {
        ItemsProviderDTO dto = new ItemsProviderDTO();

        dto.setId(rs.getLong(ItemsProvidersColumns.ID));
        dto.setName(rs.getString(ItemsProvidersColumns.NAME));
        dto.setPhoneNumber(rs.getString(ItemsProvidersColumns.PHONE_NUMBER));
        dto.setCost(rs.getDouble(ItemsProvidersColumns.COST));

        return dto;
    }

    private void handleSQLError(String message, Exception e) {
        log.error(message, e);
        throw new RuntimeException("Database error", e);
    }

    private Item mapRowToItem(ResultSet rs) throws SQLException {
        return Item.builder()
                .id(rs.getLong(ItemColumns.ID))
                .name(rs.getString(ItemColumns.NAME))
                .quantity(rs.getInt(ItemColumns.QUANTITY))
                .build();
    }

    private ItemDTO mapRowToDTO(ResultSet rs) throws SQLException {
        return ItemDTO.builder()
                .id(rs.getLong(ItemColumns.ID))
                .name(rs.getString(ItemColumns.NAME))
                .quantity(rs.getInt(ItemColumns.QUANTITY))
                .build();
    }
}
