package com.lv.api.dao.impl;

import com.lv.api.model.Item;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            log.error("Error fetching item with id: {}", id, e);
            throw new RuntimeException("Database error", e);
        }

        return Optional.empty();
    }

    private Item mapRowToItem(ResultSet rs) throws SQLException {
        return Item.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .quantity(rs.getInt("quantity"))
                .build();
    }
}
