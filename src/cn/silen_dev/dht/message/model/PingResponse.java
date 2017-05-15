package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

/**
 * Created by silen on 17-5-14.
 */
public class PingResponse extends MessageBuilder {
    public PingResponse(String t,BencodeString rId) {
        super();
        builderType(MessageType.RESPONSE);
        builderT(t);
        BencodeMap map=new BencodeMap();
        map.put(new BencodeString("id"),rId);
        builderResponse(map);
    }
}
