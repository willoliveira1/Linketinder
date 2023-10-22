package com.linketinder

import com.linketinder.context.ApplicationContext

static void main(String[] args) {

    ApplicationContext context = new ApplicationContext()
    context.generate()

    context.connectionFactory.instance().connection.close()

}
