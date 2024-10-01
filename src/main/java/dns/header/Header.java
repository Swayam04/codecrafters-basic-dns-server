package dns.header;

import java.nio.ByteBuffer;

public class Header {

    private short ID;
    private final HeaderFlags flags;
    private short QDCOUNT;
    private short ANCOUNT;
    private short NSCOUNT;
    private short ARCOUNT;

    public Header() {
        ID = 0;
        flags = new HeaderFlags();
        QDCOUNT = 0;
        ANCOUNT = 0;
        NSCOUNT = 0;
        ARCOUNT = 0;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public short getAdditionalRecordCount() {
        return ARCOUNT;
    }

    public void setAdditionalRecordCount(short ARCOUNT) {
        this.ARCOUNT = ARCOUNT;
    }

    public short getNameServerCount() {
        return NSCOUNT;
    }

    public void setNameServerCount(short NSCOUNT) {
        this.NSCOUNT = NSCOUNT;
    }

    public short getAnswerCount() {
        return ANCOUNT;
    }

    public void setAnswerCount(short ANCOUNT) {
        this.ANCOUNT = ANCOUNT;
    }

    public short getQuestionCount() {
        return QDCOUNT;
    }

    public void setQuestionCount(short QDCOUNT) {
        this.QDCOUNT = QDCOUNT;
    }

    public HeaderFlags getFlags() {
        return flags;
    }

    public byte[] buildDnsHeader() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putShort(ID);
        buffer.putShort(flags.getFlags());
        buffer.putShort(QDCOUNT);
        buffer.putShort(ANCOUNT);
        buffer.putShort(NSCOUNT);
        buffer.putShort(ARCOUNT);

        return buffer.array();
    }

}
