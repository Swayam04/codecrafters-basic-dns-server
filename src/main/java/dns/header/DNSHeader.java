package dns.header;

import java.nio.ByteBuffer;

public class DNSHeader {

    private short ID;
    private OPCode opCode;
    private RCode rCode;
    private boolean QR;
    private boolean AA;
    private boolean TC;
    private boolean RD;
    private boolean RA;
    private short QDCOUNT;
    private short ANCOUNT;
    private short NSCOUNT;
    private short ARCOUNT;

    public DNSHeader() {
        this.ID = 0;
        this.opCode = OPCode.STANDARD_QUERY;
        this.rCode = RCode.NO_ERROR;
        this.QR = false;
        this.AA = false;
        this.TC = false;
        this.RD = false;
        this.RA = false;
        this.QDCOUNT = 0;
        this.ANCOUNT = 0;
        this.NSCOUNT = 0;
        this.ARCOUNT = 0;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public short getQuestionCount() {
        return QDCOUNT;
    }

    public void setQuestionCount(short QDCOUNT) {
        this.QDCOUNT = QDCOUNT;
    }

    public short getAnswerCount() {
        return ANCOUNT;
    }

    public void setAnswerCount(short ANCOUNT) {
        this.ANCOUNT = ANCOUNT;
    }

    public short getNameServerCount() {
        return NSCOUNT;
    }

    public void setNameServerCount(short NSCOUNT) {
        this.NSCOUNT = NSCOUNT;
    }

    public short getAdditionalRecordCount() {
        return ARCOUNT;
    }

    public void setAdditionalRecordCount(short ARCOUNT) {
        this.ARCOUNT = ARCOUNT;
    }

    public boolean isQR() {
        return QR;
    }

    public void setQR(boolean QR) {
        this.QR = QR;
    }

    public boolean isAA() {
        return AA;
    }

    public void setAA(boolean AA) {
        this.AA = AA;
    }

    public boolean isTC() {
        return TC;
    }

    public void setTC(boolean TC) {
        this.TC = TC;
    }

    public boolean isRD() {
        return RD;
    }

    public void setRD(boolean RD) {
        this.RD = RD;
    }

    public boolean isRA() {
        return RA;
    }

    public void setRA(boolean RA) {
        this.RA = RA;
    }

    public OPCode getOpCode() {
        return opCode;
    }

    public void setOpCode(OPCode opCode) {
        this.opCode = opCode;
    }

    public RCode getRCode() {
        return rCode;
    }

    public void setRCode(RCode rCode) {
        this.rCode = rCode;
    }

    public byte[] buildDnsHeader() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putShort(ID);
        buffer.putShort(getFlags());
        buffer.putShort(QDCOUNT);
        buffer.putShort(ANCOUNT);
        buffer.putShort(NSCOUNT);
        buffer.putShort(ARCOUNT);
        return buffer.array();
    }

    private short getFlags() {
        int flags = 0;
        flags |= (QR ? 1 : 0) << 15;
        flags |= (opCode.getValue() & 0xF) << 11;
        flags |= (AA ? 1 : 0) << 10;
        flags |= (TC ? 1 : 0) << 9;
        flags |= (RD ? 1 : 0) << 8;
        flags |= (RA ? 1 : 0) << 7;
        flags |= (rCode.getValue() & 0xF);
        return (short) flags;
    }
}

