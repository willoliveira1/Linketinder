package com.linketinder


import com.linketinder.database.DatabaseConnection

static void main(String[] args) {

    ApplicationContext context = new ApplicationContext()
    context.generate()

    DatabaseConnection.instance().connection.close()

}
