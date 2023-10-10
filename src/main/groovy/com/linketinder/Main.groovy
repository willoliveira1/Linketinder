package com.linketinder

import com.linketinder.database.DatabaseFactory
import com.linketinder.view.ApplicationView

static void main(String[] args) {

    ApplicationView application = new ApplicationView()
    application.applicationGenerate()

    DatabaseFactory.instance().connection.close()

}
