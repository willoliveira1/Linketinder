package com.linketinder.util

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLDataException

class QueryHelper {

     static int idFinder(PreparedStatement stmt) {
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("id")
        }
        throw new SQLDataException("Id não encontrado.")
    }

}
