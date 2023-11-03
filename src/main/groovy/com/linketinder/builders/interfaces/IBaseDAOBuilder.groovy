package com.linketinder.builders.interfaces

import com.linketinder.database.interfaces.IConnection

interface IBaseDAOBuilder<T> {

    IBaseDAOBuilder<T> withConnection(IConnection connection)
    T build()

}
