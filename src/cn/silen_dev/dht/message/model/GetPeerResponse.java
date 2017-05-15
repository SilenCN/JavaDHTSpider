package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

/**
 * Created by silen on 17-5-13.
 */
public class GetPeerResponse extends MessageBuilder {
    public GetPeerResponse(String myId,String queryId,String oneNode) {
        super();
        builderType(MessageType.RESPONSE);
        builderT("get_peer");
        BencodeMap map=new BencodeMap();
        map.put(new BencodeString("id"),new BencodeString(queryId));
        map.put(new BencodeString("token"),new BencodeString("SILENDEV"));
        map.put(new BencodeString("nodes"),new BencodeString(oneNode));
        builderResponse(map);
    }
}
