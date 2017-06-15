package cn.silen_dev.dht.queue;

import cn.silen_dev.dht.message.presenter.MessageManager;
import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by silen on 17-5-15.
 */
public class PacketQueue extends Thread {
    private static Object monitor = new Object();
    Queue<DatagramPacket> packetQueue;
    MessageManager messageManager;
    private OnPacketPresenterListener onPacketPresenterListener;

    public PacketQueue(MessageManager messageManager) {
        super();
        packetQueue = new LinkedList<>();
        this.messageManager = messageManager;
    }

    @Override
    public void start() {
        super.start();

        while (true) {
          //  synchronized (monitor) {
                //System.out.println("Packet运行");
                DatagramPacket packet = packetQueue.poll();
                if (null != packet) {
                    messageManager.messageParse(packet);
                    System.out.println("Packet运行");
                /*if (null!=onPacketPresenterListener){
                    onPacketPresenterListener.on(packet);
                }*/
                } /*else {
                    try {
                        sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
           // }
        }
    }

    public void addPacket(DatagramPacket packet) {
     //   packetQueue.add(packet);
     //   System.out.println("Packet运行1");
        if (null != packet) {
            messageManager.messageParse(packet);
            System.out.println("Packet运行");
        }
    }

    public void setOnPacketPresenterListener(OnPacketPresenterListener onPacketPresenterListener) {
        this.onPacketPresenterListener = onPacketPresenterListener;
    }

    public interface OnPacketPresenterListener {
        void on(DatagramPacket packet);
    }
}
