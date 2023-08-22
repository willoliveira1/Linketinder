package com.linketinder.util

class ObjectHandler {

    static Integer getLastId(List<Object> objects) {
        int highestId = 0
        for (Object object : objects) {
            if (object.id > highestId) {
                highestId = object.id
            }
        }
        return highestId
    }

    static Integer getNextId(List<Object> objects) {
        return getLastId(objects) + 1
    }

}
