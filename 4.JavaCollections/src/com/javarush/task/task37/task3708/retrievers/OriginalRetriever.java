package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.storage.RemoteStorage;
import com.javarush.task.task37.task3708.storage.Storage;

public class OriginalRetriever implements Retriever {
    Storage storage;

    public OriginalRetriever(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Object retrieve(long id) {
        Object object = storage.get(id);

        RemoteStorage remoteStorage = new RemoteStorage();

//        System.out.println("Original 14: id = "+id+" object = "+ object);
        return object;
    }
}
