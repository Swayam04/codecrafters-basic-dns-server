package utils;

import dns.DNSMessage;

public class DNSMessageParser {

    public static DNSMessage parseMessage(byte[] message) {
        return new DNSMessage.Builder().build();
    }

}
