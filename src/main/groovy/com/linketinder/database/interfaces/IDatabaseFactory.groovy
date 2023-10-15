package com.linketinder.database.interfaces

import groovy.sql.Sql

interface IDatabaseFactory {

    Sql newInstance()
    Sql instance()

}
