package cn.silen_dev.dht.message.model;

import cn.silen_dev.dht.bittorrent.bencode.BencodeMap;
import cn.silen_dev.dht.bittorrent.bencode.BencodeString;

/**
 * Created by silen on 17-5-13.
 */
public class MessageBuilder {
    BencodeMap map;
    public MessageBuilder() {
        map=new BencodeMap();
    }
    public MessageBuilder builderType(String type){
        map.put(new BencodeString("y"),new BencodeString(type));
        return this;
    }
    public MessageBuilder builderT(String id){
        map.put(new BencodeString(MessageType.TRANSACTION),new BencodeString(id));
        return this;
    }
    public MessageBuilder builderQuery(String query_type){
        map.put(new BencodeString(MessageType.QUERY),new BencodeString(query_type));
        return this;
    }
    public MessageBuilder builderArgs(BencodeMap args){
        map.put(new BencodeString("a"),args);
        return this;
    }
    public MessageBuilder builderResponse(BencodeMap args){
        map.put(new BencodeString(MessageType.RESPONSE),args);
        return this;
    }
    public BencodeMap toMap(){
        return this.map;
    }
}
