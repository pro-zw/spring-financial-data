package org.wzstudio.spring.finance.data.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackages = "org.wzstudio.spring.finance.data.service, org.wzstudio.spring.finance.data.dao, org.wzstudio.spring.finance.data.domain")
public class SpringRootConfig {

    @Bean
    public HikariDataSource dataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(20);
        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        dataSource.addDataSourceProperty("serverName", "localhost");
        dataSource.addDataSourceProperty("portNumber", "5432");
        dataSource.addDataSourceProperty("databaseName", "finance_data");
        dataSource.addDataSourceProperty("user", "weizheng");
        dataSource.addDataSourceProperty("password", "password");
        return dataSource;
    }

    @Bean
    public Logger loggerFactory() {
        return LogManager.getLogger("org.wzstudio.spring.finance.data");
    }

    /*
    @Bean
    public FormattingConversionService conversionService() {
        // Use the DefaultFormattingConversionService but do not register defaults
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(false);

        // Ensure @NumberFormat is still supported
        conversionService.addFormatterForFieldAnnotation(
                new NumberFormatAnnotationFormatterFactory()
        );

        // Register date conversion with a specific global format
        DateFormatterRegistrar registrar = new DateFormatterRegistrar();
        registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
        registrar.registerFormatters(conversionService);

        return conversionService;
    }
    */
}
