package cn.silen_dev.dht.queue;


import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by silen on 17-5-13.
 */
public class NodeList {
    ArrayList<InetSocketAddress> nodeList;
    Set addressSet;
    private static final int QUEUE_MAX_LENGTH = 1000;
    public NodeList() {
        super();
        nodeList=new ArrayList<>();
        addressSet=new HashSet<>();
    }
    public synchronized boolean add(InetSocketAddress inetSocketAddress){
        int size=addressSet.size();
        long ipInt=Long.parseLong(inetSocketAddress.getHostString().replace(".","").trim());
        addressSet.add(ipInt);
        if (addressSet.size()==size)
            return false;
        nodeList.add(inetSocketAddress);
        System.out.println(size+1);
        return true;
    }
    public synchronized ArrayList<InetSocketAddress> getAll(){
        return nodeList;
    }
}
