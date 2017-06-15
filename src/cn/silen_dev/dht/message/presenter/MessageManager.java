package cn.silen_dev.dht.message.presenter;

import cn.silen_dev.dht.bittorrent.bencode.*;
import cn.silen_dev.dht.main.MainConst;
import cn.silen_dev.dht.message.model.FindNodeMessage;
import cn.silen_dev.dht.message.model.FindNodeResponse;
import cn.silen_dev.dht.message.model.GetPeerResponse;
import cn.silen_dev.dht.message.model.PingResponse;
import cn.silen_dev.dht.queue.NodeList;
import cn.silen_dev.dht.socket.cache.UDPSendCache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by silen on 17-5-13.
 */

public class MessageManager {
    UDPSendCache udpSendCache;
    NodeList nodeList;

    public MessageManager(UDPSendCache udpSendCache, NodeList nodeList) {
        super();
        this.udpSendCache = udpSendCache;
        this.nodeList = nodeList;
    }

    public synchronized void messageParse(DatagramPacket packet) {
        try {
            BencodeObject o = Bencode.parseBencode(new ByteArrayInputStream(packet.getData(), 0, packet.getLength()));
            if (((BencodeMap) o).get(new BencodeString("y")).equals(new BencodeString("r"))) {
                String t = ((BencodeMap) o).get(new BencodeString("t")).toString();
                System.out.println(t);
                BencodeMap r = (BencodeMap) ((BencodeMap) o).get(new BencodeString("r"));
                if (t.equals("ping")) {

                }
                if (t.equals("find_node")) {
                    BencodeString rid = (BencodeString) r.get(new BencodeString("id"));
                    BencodeString nodes = (BencodeString) r.get(new BencodeString("nodes"));
                    if (null != nodes) {
                        byte[] bs = nodes.getBytes();
                        for (int i = 0; i + 26 < bs.length; i += 26) {
                            byte[] addr = new byte[4];
                            System.arraycopy(bs, i + 20, addr, 0, 4);
                            InetAddress inetAddress = InetAddress.getByAddress(addr);
                            byte[] port = new byte[2];
                            System.arraycopy(bs, i + 24, port, 0, 2);
                            int iPort = ((0xff & port[0]) << 8) + (0xff & port[1]);
                            System.out.println(inetAddress.getHostAddress() + ":" + iPort);
                            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, iPort);
                            if (nodeList.add(inetSocketAddress)) {
                                FindNodeMessage message = new FindNodeMessage(MainConst.MY_ID, MainConst.FIND_NODE_ID);
                                udpSendCache.addSendMessage(inetSocketAddress, message);
                            }
                        }
                    }
                }
                if (t.equals("get_peers")) {

                    //  announcePeer(rem, (BencodeString)r.get(new BencodeString("token")));
                }
            } else if (((BencodeMap) o).get(new BencodeString("y")).equals(new BencodeString("e"))) {
                BencodeList e = (BencodeList) (((BencodeMap) o).get(new BencodeString("e")));
                System.out.print("Error: ");
                for (BencodeObject bo : e) {
                    System.out.print(bo + " ");
                }
                System.out.println();
            } else if (((BencodeMap) o).get(new BencodeString("y")).equals(new BencodeString("q"))) {
                System.out.println(Bencode.parseBencode(new ByteArrayInputStream(packet.getData(), 0, packet.getLength())));
                String r = ((BencodeMap) o).get(new BencodeString("q")).toString();
                BencodeMap a = (BencodeMap) ((BencodeMap) o).get(new BencodeString("a"));
                BencodeString rid = (BencodeString) a.get(new BencodeString("id"));
                String t = ((BencodeMap) o).get(new BencodeString("t")).toString();
                if (r.equals("ping")) {
                    PingResponse response = new PingResponse(t, rid);
                    udpSendCache.addSendMessage((InetSocketAddress) packet.getSocketAddress(), response);
                    System.out.println("收到Ping:" + ((InetSocketAddress) packet.getSocketAddress()).getHostName());
                }
                if (r.equals("get_peers")) {
                    BencodeString infoHash = (BencodeString) a.get(new BencodeString("info_hash"));
                    System.out.println("收到Peers：" + infoHash.toString());
                    GetPeerResponse getPeerResponse = new GetPeerResponse(t, rid);
                    udpSendCache.addSendMessage((InetSocketAddress) packet.getSocketAddress(), getPeerResponse);
                }
                if (r.equals("find_node")) {
                    FindNodeResponse findNodeResponse = new FindNodeResponse(t, rid);
                    udpSendCache.addSendMessage((InetSocketAddress) packet.getSocketAddress(), findNodeResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
