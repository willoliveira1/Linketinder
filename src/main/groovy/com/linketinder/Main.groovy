package com.linketinder


import com.linketinder.database.DatabaseFactory

static void main(String[] args) {

    ApplicationContext context = new ApplicationContext()
    context.generate()

    DatabaseFactory.instance().connection.close()

}
