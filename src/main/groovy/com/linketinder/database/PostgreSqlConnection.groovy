package com.linketinder.database

import com.linketinder.database.interfaces.IConnection
import groovy.sql.Sql
import java.util.logging.Level
import java.util.logging.Logger
import java.sql.SQLException

class PostgreSqlConnection implements IConnection {

    private static PostgreSqlConnection dbInstance
    private Sql connection

    private PostgreSqlConnection() {
        String driver = "org.postgresql.Driver"
        String url = "jdbc:postgresql://localhost:5432/linketinder"
        String user = "postgres"
        String password = "postgres"

        try {
            this.connection = Sql.newInstance(url, user, password, driver)
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, null, e.printStackTrace())
        }
    }

    static PostgreSqlConnection getDBInstance() {
        if (dbInstance == null) {
            dbInstance = new PostgreSqlConnection()
        }
        return dbInstance
    }

    Sql instance() {
        return this.connection
    }

}
