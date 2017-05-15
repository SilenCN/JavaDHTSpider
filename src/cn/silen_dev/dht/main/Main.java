package cn.silen_dev.dht.main;

import cn.silen_dev.dht.bittorrent.bencode.*;
import cn.silen_dev.dht.message.model.FindNodeMessage;
import cn.silen_dev.dht.message.model.PingMessage;
import cn.silen_dev.dht.message.presenter.MessageManager;
import cn.silen_dev.dht.queue.NodeList;
import cn.silen_dev.dht.socket.server.DHTSocketServer;
import sun.rmi.runtime.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/*
*-612109583056279876430458948208545213272273165486
114.134.124.143
56372
-435608560864534752037529897313369286756127438998
151.235.80.156
28870
-529472351447996981140480108308144707312818709664
114.245.206.111
16001
308943041219627741599067894775739270820869794064
5.19.7.194
36255
236659533628804564546870830891561186179972028294
95.27.242.99
35558
272833125671481166262909603555098833556684545587
113.201.16.19
50982
581680661250775092902896307117896568262931319458
85.84.64.24
25195
-161328155974662876908678509618639721608509100365
213.21.32.25
36838
-508663882657441567900290433315738645556699682349
125.224.128.101
1739
517775520784106940041743719581026322567741472935
114.165.237.31
24539
-359895117576686221128030842475701578236488808025
61.54.24.36
65001
-399988573601859968033011033608275177216199330179
62.182.200.31
8253
126163455548613194624810135764057135837293296470
14.33.124.142
34838
575793755851673010389366703324925679452821858338
84.0.188.107
60222
7978417085728651376783572989853620955011967505
112.104.16.114
7986
 */
public class Main {
    private static final List<InetSocketAddress> BOOTSTRAP_NODES = new ArrayList<>(Arrays.asList(
            new InetSocketAddress("router.bittorrent.com", 6881),
            new InetSocketAddress("router.utorrent.com", 6881),
            new InetSocketAddress("dht.transmissionbt.com",6881)
    ));

    public static void main(String[] args) throws Exception {

        String myId = SHA1("SILEN").substring(0, 20);
        MainConst.MY_ID=myId;
        String target = SHA1("TEST").substring(0, 20);
        MainConst.FIND_NODE_ID=target;
        System.out.println(myId);
        FindNodeMessage findNodeMessage = new FindNodeMessage(myId, target);
        PingMessage pingMessage = new PingMessage(myId);
        DHTSocketServer socketServer = new DHTSocketServer();
        NodeList nodeList=new NodeList();
        MessageManager messageManager=new MessageManager(socketServer,nodeList);
        socketServer.start();
        socketServer.setOnServerMessageListener(packet -> {
            try {
                messageManager.messageParse(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // recvPacket(packet);
        });
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时");
                for (InetSocketAddress inetSocketAddress:nodeList.getAll()){

                    socketServer.seedPacket(inetSocketAddress, pingMessage);
                    socketServer.seedPacket(inetSocketAddress, findNodeMessage);
                }
            }
        };

        for (InetSocketAddress address : BOOTSTRAP_NODES) {
            socketServer.seedPacket(address, findNodeMessage);
            socketServer.seedPacket(address, pingMessage);
        }
        timer.schedule(timerTask,5000,5000);

    }

    public final static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
