package cn.silen_dev.dht.socket.cache;

import cn.silen_dev.dht.message.model.MessageBuilder;
import cn.silen_dev.dht.socket.model.UDPSendMessage;
import cn.silen_dev.dht.socket.server.DHTSocketServer;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by silen on 17-5-15.
 */
public class UDPSendCache extends Thread {
    private static Object monitor = new Object();
    Queue<UDPSendMessage> sendMessageQueue;
    boolean flag = true;
    DHTSocketServer socket;

    public UDPSendCache(DHTSocketServer socket) {
        super();
        sendMessageQueue = new LinkedList<>();
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        while (flag) {
            //   synchronized (monitor) {
            UDPSendMessage message = sendMessageQueue.poll();
            if (null != message) {
                System.out.println("UDP发送");
                socket.seedPacket(message.getInetSocketAddress(), message.getMessageBuilder());
                 /*   try {
                        sleep(50);
                    } catch (InterruptedException e) {
                       */ //e.printStackTrace();
            }
        } /*else {
                    try {
                       sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            //    }

            */
    }

    public void addSendMessage(UDPSendMessage message) {
        System.out.println("UDP添加");
        sendMessageQueue.add(message);

    }

    public void addSendMessage(InetSocketAddress address, MessageBuilder builder) {
      //  addSendMessage(new UDPSendMessage(builder, address));
        socket.seedPacket(address, builder);
        System.out.println("UDP发送");
    }
}
