package com.linketinder.database.interfaces

import groovy.sql.Sql

interface IDatabaseConnection {

    Sql createConnection()
    Sql instance()

}
