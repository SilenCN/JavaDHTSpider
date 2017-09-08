package cn.silen_dev.dht.queue;

import cn.silen_dev.dht.cache.CacheQueue;
import cn.silen_dev.dht.message.presenter.MessageManager;

import java.net.DatagramPacket;

/**
 * Created by silen on 17-5-15.
 */
public class PacketQueue extends Thread {
    CacheQueue<DatagramPacket> packetQueue;
    MessageManager messageManager;
    private OnPacketPresenterListener onPacketPresenterListener;

    public PacketQueue(MessageManager messageManager) {
        super();
        packetQueue = new CacheQueue<>();
        this.messageManager = messageManager;
    }


    @Override
    public void run() {

        DatagramPacket packet;
        System.out.println("Packet运行");

        while (true) {
          /*  try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           packet = packetQueue.poll();

            if (null != packet) {
                try {
                    messageManager.messageParse(packet);
                }catch (Exception e){
                    e.printStackTrace();
                }

           }

        }
    }

    public void addPacket(DatagramPacket packet) {
        messageManager.messageParse(packet);
     //   packetQueue.add(packet);
     //   System.out.println("Packet运行1");
/*        synchronized (packetQueue) {
            if (null != packet) {
                messageManager.messageParse(packet);
                System.out.println("Packet添加");
                packetQueue.notify();
            }

        }*/
    }

    public void setOnPacketPresenterListener(OnPacketPresenterListener onPacketPresenterListener) {
        this.onPacketPresenterListener = onPacketPresenterListener;
    }

    public interface OnPacketPresenterListener {
        void on(DatagramPacket packet);
    }

}
