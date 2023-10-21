package com.linketinder.database.interfaces

import groovy.sql.Sql

interface IConnection {

    Sql createConnection()
    Sql instance()

}
