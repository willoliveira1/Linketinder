package com.linketinder.fileprocessor

interface Processor<T> {

    void writeFile(List<T> t)
    T readById(Integer id)
    List<T> readFile()
    void add(T t)
    void update(Integer id, T t)
    void delete(Integer id)

}
