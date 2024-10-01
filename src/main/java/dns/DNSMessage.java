package dns;

import dns.header.Header;

public class DNSMessage {

    private final byte[] message = new byte[512];
    private final Header header;

    public DNSMessage() {
        header = new Header();
    }

    public Header getHeader() {
        return header;
    }

    public byte[] getMessage() {
        int pos = 0;
        byte[] dnsHeader = header.buildDnsHeader();
        System.arraycopy(dnsHeader, 0, message, pos, dnsHeader.length);
        return message;
    }
}
