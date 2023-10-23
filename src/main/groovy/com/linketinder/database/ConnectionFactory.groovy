package com.linketinder.database

import com.linketinder.database.interfaces.IConnection

abstract class ConnectionFactory {

    static IConnection createConnection(String dbType) {
        switch (dbType) {
            case "POSTGRESQL":
                return PostgreSqlConnection.getDBInstance()
            default:
                throw new RuntimeException("Invalid database")
        }
    }

}
