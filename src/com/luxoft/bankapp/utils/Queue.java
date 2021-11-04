package com.luxoft.bankapp.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue<T> {
    private List<T> list = Collections.synchronizedList(new LinkedList<>());

    public void add(T object) {
        list.add(object);
    }

    public T get() {
        if (list.size() > 0) {
            return list.remove(0);
        } else {
            return null;
        }
    }
}
