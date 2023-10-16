package com.linketinder.database

import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IDatabaseConnection
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLDataException

class DBService implements IDBService {

    IDatabaseConnection databaseFactory
    Sql sql = databaseFactory.instance()

    DBService(IDatabaseConnection databaseFactory) {
        this.databaseFactory = databaseFactory
    }

    int idFinder(String table, String column, String value) {
        String query = "SELECT id FROM ${table} WHERE ${column} = '${value}'"
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("id")
        }
        throw new SQLDataException("Id não encontrado.")
    }

}
