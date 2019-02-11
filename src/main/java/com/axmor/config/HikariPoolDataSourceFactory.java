package com.axmor.config;
import java.sql.Driver;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

public class HikariPoolDataSourceFactory implements DataSourceFactory,
        ConnectionProperties {
    private final HikariDataSource dataSource;

    public HikariPoolDataSourceFactory() {
        this(HikariUtils.newDefaultDS());
    }

    public HikariPoolDataSourceFactory(HikariDataSource dataSource) {
        assert dataSource != null;
        this.dataSource = dataSource;
    }

    public ConnectionProperties getConnectionProperties() {
        return this;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void setUsername(String username) {
        dataSource.setUsername(username);
    }

    public void setPassword(String password) {
        dataSource.setPassword(password);
    }

    public void setUrl(String url) {
        dataSource.setJdbcUrl(url);
    }

    public void setDriverClass(Class<? extends Driver> driverClass) {
        try {
            dataSource.setDriverClassName(driverClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}