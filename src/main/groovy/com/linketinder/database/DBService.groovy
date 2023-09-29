package com.linketinder.database

import groovy.sql.Sql

import java.sql.PreparedStatement
import java.sql.ResultSet

class DBService {

    Sql sql = DatabaseFactory.instance()

    int idFinder(String table, String column, String value) {
        String query = "SELECT id FROM ${table} WHERE ${column} = '${value}'"
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("id")
        }
    }



}
