package com.linketinder

static void main(String[] args) {

    ApplicationContext context = new ApplicationContext()
    context.generate()

    context.databaseConnection.instance().connection.close()

}
