package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

import static cn.silen_dev.dht.main.Main.SHA1;

/**
 * Created by silen on 17-5-13.
 */
public class FindNodeMessage extends MessageBuilder {
    public FindNodeMessage(String myId, String targetId) {
        super();
        builderT("find_node");
        builderQuery(MessageType.QUERY_FIND_NODES);
        builderType(MessageType.QUERY);
        BencodeMap map = new BencodeMap();
        map.put(new BencodeString("id"), new BencodeString(myId));
        map.put(new BencodeString("target"), new BencodeString(SHA1((int)(Math.random()*100000)+"").substring(0, 20)));
        builderArgs(map);
    }
}
