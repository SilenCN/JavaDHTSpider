package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

/**
 * Created by silen on 17-5-13.
 */
public class PingMessage extends MessageBuilder {
    public PingMessage(String myId) {
        super();
        builderType(MessageType.QUERY);
        builderT("ping");
        builderQuery(MessageType.QUERY_PING);
        BencodeMap map=new BencodeMap();
        map.put(new BencodeString("id"),new BencodeString(myId));
        builderArgs(map);
    }
}
