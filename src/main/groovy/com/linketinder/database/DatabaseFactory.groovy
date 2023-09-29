package com.linketinder.database

import java.sql.SQLException
import groovy.sql.Sql
import java.util.logging.Level
import java.util.logging.Logger

class DatabaseFactory {

    static Sql instance() {
        try {
            String driver = "org.postgresql.Driver"
            String url = "jdbc:postgresql://localhost:5432/linketinder"
            String user = "postgres"
            String password = "postgres"

            return Sql.newInstance(url, user, password, driver)
        } catch (ClassNotFoundException | SQLException e) {
            return Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }


}
