package dns.header;

import java.nio.ByteBuffer;

public class DNSHeader {

    private short ID = 0;
    private OPCode opCode = OPCode.STANDARD_QUERY;
    private RCode rCode = RCode.NO_ERROR;
    private boolean QR = false;
    private boolean AA = false;
    private boolean TC = false;
    private boolean RD = false;
    private boolean RA = false;
    private short questionCount = 0;
    private short answerCount = 0;
    private short authorityCount = 0;
    private short additionalCount = 0;


    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public short getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(short QDCOUNT) {
        this.questionCount = QDCOUNT;
    }

    public short getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(short ANCOUNT) {
        this.answerCount = ANCOUNT;
    }

    public short getNameServerCount() {
        return authorityCount;
    }

    public void setNameServerCount(short NSCOUNT) {
        this.authorityCount = NSCOUNT;
    }

    public short getAdditionalRecordCount() {
        return additionalCount;
    }

    public void setAdditionalRecordCount(short ARCOUNT) {
        this.additionalCount = ARCOUNT;
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
        buffer.putShort(questionCount);
        buffer.putShort(answerCount);
        buffer.putShort(authorityCount);
        buffer.putShort(additionalCount);
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

