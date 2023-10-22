package com.linketinder.context.builder.interfaces

import com.linketinder.database.interfaces.IDBService

interface IDAOBuilder<T> extends IBaseDAOBuilder<T> {

    IDAOBuilder<T> withDBService(IDBService dbService)

}
