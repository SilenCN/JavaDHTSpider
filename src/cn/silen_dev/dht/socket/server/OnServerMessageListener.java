package cn.silen_dev.dht.socket.server;

import java.net.DatagramPacket;

/**
 * Created by silen on 17-5-12.
 */
public interface OnServerMessageListener {
    void onMessage(DatagramPacket packet);
}
