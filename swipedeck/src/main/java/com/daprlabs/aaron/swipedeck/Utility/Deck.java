package com.daprlabs.aaron.swipedeck.Utility;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by aaron on 21/08/2016.
 */
public class Deck<T> implements List<T> {

    private ArrayList<T> internalList = new ArrayList<>();


    private ListEventListener listener;

    public Deck(ListEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void add(int location, T object) {
        internalList.add(location, object);
        listener.itemAdded(object, location);
    }

    //reactive
    @Override
    public boolean add(T object) {
        T itemToBeAdded = (T) object;
        boolean added = internalList.add(itemToBeAdded);
        listener.itemAdded(object, internalList.indexOf(itemToBeAdded));

        return added;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        return internalList.addAll(location, collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return internalList.addAll(collection);
    }

    //reacts
    @Override
    public void clear() {
        for(T t : internalList){
            listener.itemRemoved(t);
        }
        internalList.clear();
    }

    //
    @Override
    public boolean contains(Object object) {
        return internalList.contains(object);
    }

    //
    @Override
    public boolean containsAll(Collection<?> collection) {
        return internalList.containsAll(collection);
    }

    //
    @Override
    public T get(int location) {
        return internalList.get(location);
    }

    //
    @Override
    public int indexOf(Object object) {
        return internalList.indexOf(object);
    }

    //
    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    //
    @NonNull
    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    //
    @Override
    public int lastIndexOf(Object object) {
        return internalList.lastIndexOf(object);
    }

    //?
    @Override
    public ListIterator<T> listIterator() {
        return internalList.listIterator();
    }

    //?
    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        return internalList.listIterator(location);
    }

    //reactive, will fail if index oob
    @Override
    public T remove(int location) {
        T itemToRemove = internalList.get(location);
        listener.itemRemoved(itemToRemove);
        internalList.remove(itemToRemove);

        //if something was removed let the listener know some items changed position
        for (int i = location; i < internalList.size(); ++i) {
            listener.itemPositionChanged(internalList.get(i), i);
        }
        return itemToRemove;
    }

    //reactive, may need to check for object in future
    //this honestly may need debugging, the logic seems sound atm though.
    @Override
    public boolean remove(Object object) {
        T itemToRemove = (T) object;
        int index = internalList.indexOf(itemToRemove);
        boolean removed = internalList.remove(itemToRemove);

        if(removed){
            for(int i = index; i<internalList.size(); ++i){
                listener.itemPositionChanged(internalList.get(i), i);
            }
            listener.itemRemoved(object);
        }
        return removed;
    }

    //not supported yet
    @Override
    public boolean removeAll(Collection<?> collection) {
        try {
            throw new UnsupportedOperationException("operation not yet supported!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return internalList.removeAll(collection);
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return internalList.retainAll(collection);
    }

    @Override
    public T set(int location, T object) {
        return internalList.set(location, object);
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        return subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return internalList.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return internalList.toArray(array);
    }

    public interface ListEventListener {
        void itemAdded(Object object, int position);
        void itemRemoved(Object object);
        void itemPositionChanged(Object object, int position);

    }
}
