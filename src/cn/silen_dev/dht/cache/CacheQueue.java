package cn.silen_dev.dht.cache;

import java.util.LinkedList;

public class CacheQueue<E> extends LinkedList<E>{

    @Override
    public synchronized boolean add(E e) {
        boolean temp=super.add(e);
        notifyAll();
        return temp;
    }


    @Override
    public synchronized E poll() {
        if (size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        E e=super.poll();
        notify();
        return e;
    }
}