package com.linketinder.database


import com.linketinder.database.interfaces.IConnection
import groovy.sql.Sql
import java.util.logging.Level
import java.util.logging.Logger
import java.sql.SQLException

class PostgreSqlConnection implements IConnection {

    Sql createConnection() {
        String driver = "org.postgresql.Driver"
        String url = "jdbc:postgresql://localhost:5432/linketinder"
        String user = "postgres"
        String password = "postgres"
        return Sql.newInstance(url, user, password, driver)
    }

    Sql instance() {
        try {
            return this.createConnection()
        } catch (ClassNotFoundException | SQLException e) {
            return Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, null, e)
        }
    }

}
