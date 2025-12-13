package com.lv;

import com.lv.api.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Data;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @Data
    @Singleton
    static class OnStartupEventListener implements ApplicationEventListener<StartupEvent> {

        @Inject
        private Environment environment;

        @Override
        public void onApplicationEvent(StartupEvent event) {
            environment.logConfig();
            environment.validateConfig();
        }

    }
}
