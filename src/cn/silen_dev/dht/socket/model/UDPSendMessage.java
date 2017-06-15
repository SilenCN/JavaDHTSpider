package cn.silen_dev.dht.socket.model;

import cn.silen_dev.dht.message.model.MessageBuilder;

import java.net.InetSocketAddress;

/**
 * Created by silen on 17-5-15.
 */
public class UDPSendMessage {
    private MessageBuilder messageBuilder;
    private InetSocketAddress inetSocketAddress;

    public UDPSendMessage(MessageBuilder messageBuilder,InetSocketAddress inetSocketAddress) {
        super();
        this.messageBuilder=messageBuilder;
        this.inetSocketAddress=inetSocketAddress;
    }

    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }
}
