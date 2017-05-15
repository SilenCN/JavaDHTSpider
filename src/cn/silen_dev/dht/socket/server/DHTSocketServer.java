package cn.silen_dev.dht.socket.server;

import cn.silen_dev.dht.message.model.MessageBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by silen on 17-5-12.
 */
public class DHTSocketServer extends Thread {
    private static final int SERVER_PORT = 12761;
    private static final int PACKET_LEN = 10 * 1024;
    private OnServerMessageListener onServerMessageListener;
    private DatagramSocket socket;
    private boolean flag;

    public DHTSocketServer() throws SocketException {
        socket = new DatagramSocket(SERVER_PORT);
        flag = true;
    }

    @Override
    public void run() {
        super.run();

        byte[] buf = new byte[PACKET_LEN];

        while (true) {
            try {
                DatagramPacket p = new DatagramPacket(buf, PACKET_LEN);
                socket.receive(p);
                if (null != onServerMessageListener) {
                    onServerMessageListener.onMessage(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnServerMessageListener(OnServerMessageListener onServerMessageListener) {
        this.onServerMessageListener = onServerMessageListener;
    }

    public void seedPacket(InetSocketAddress address, MessageBuilder messageBuilder) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            messageBuilder.toMap().write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = out.toByteArray();

        try {
            socket.send(new DatagramPacket(buf, buf.length, address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
