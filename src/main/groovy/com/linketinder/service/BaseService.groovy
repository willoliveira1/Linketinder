package com.linketinder.service

interface BaseService<T> {

    List<T> getAll()
    T getById(Integer id)
    void add(T t)
    void update(Integer id, T t)
    void delete(int id)

}
