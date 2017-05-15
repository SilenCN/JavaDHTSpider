package cn.silen_dev.dht.message.model;

/**
 * Created by silen on 17-5-12.
 */
public class MessageType {
    public static final String RESPONSE = "r";
    public static final String QUERY = "q";
    public static final String ERROR = "e";
    public static final String TRANSACTION = "t";
    public static final String QUERY_PING = "ping";
    public static final String QUERY_FIND_NODES = "find_node";
    public static final String QUERY_GET_PEERS = "get_peers";
    public static final String QUERY_ANNOUNCE_PEER = "announce_peer";

}
