package com.axmor.config;
import com.zaxxer.hikari.HikariDataSource;

public class HikariUtils{

    public static  HikariDataSource newDefaultDS() {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(10);
        return ds;
    }
}