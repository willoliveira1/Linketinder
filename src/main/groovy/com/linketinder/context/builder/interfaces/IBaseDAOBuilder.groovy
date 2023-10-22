package com.linketinder.context.builder.interfaces

import com.linketinder.database.interfaces.IConnection

interface IBaseDAOBuilder<T> {

    IBaseDAOBuilder<T> withConnection(IConnection connectionFactory)
    T build()

}
