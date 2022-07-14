package com.kongkongye.backend.permission.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 语言国际化配置
 */
@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("classpath:org/springframework/security/messages");
        return messageSource;
    }
}
