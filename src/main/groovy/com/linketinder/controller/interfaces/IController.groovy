package com.linketinder.controller.interfaces

interface IController<T> {

    List<T> getAll()
    T getById(int id)
    void add(T obj)
    void update(int id, T obj)
    void delete(int id)

}
