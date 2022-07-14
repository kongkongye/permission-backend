package com.kongkongye.backend.permission.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Slf4j
@Configuration
public class SessionConfig implements ApplicationListener<AbstractSessionEvent> {
    @Override
    public void onApplicationEvent(AbstractSessionEvent event) {
        if (event instanceof SessionCreatedEvent) {
            log.info("spring session created：" + event.getSessionId());
        } else if (event instanceof SessionDestroyedEvent) {
            log.info("spring session destroyed：" + event.getSessionId());
        }
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
