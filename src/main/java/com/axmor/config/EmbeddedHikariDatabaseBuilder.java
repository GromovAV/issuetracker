package com.axmor.config;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedHikariDatabaseBuilder extends EmbeddedDatabaseBuilder {
    public EmbeddedHikariDatabaseBuilder() {
        setDataSourceFactory(new HikariPoolDataSourceFactory());
    }
}