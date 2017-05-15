package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

/**
 * Created by silen on 17-5-15.
 */
public class FindNodeResponse extends MessageBuilder {
    public FindNodeResponse(String t,BencodeString id) {
        super();
        builderType(MessageType.RESPONSE);
        builderT(t);
        BencodeMap map=new BencodeMap();
        map.put(new BencodeString("id"),id);
        map.put(new BencodeString("nodes"),new BencodeString(""));
        builderResponse(map);
    }
}
