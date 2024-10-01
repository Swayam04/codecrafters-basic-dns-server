package dns.header;

public class HeaderFlags {
    private OPCode opCode;
    private RCode rCode;
    private boolean QR;
    private boolean AA;
    private boolean TC;
    private boolean RD;
    private boolean RA;

    public HeaderFlags() {
        opCode = OPCode.STANDARD_QUERY;
        rCode = RCode.NO_ERROR;
        QR = false;
        AA = false;
        TC = false;
        RD = false;
        RA = false;
    }

    public boolean isRecursionAvailable() {
        return RA;
    }

    public void setRecursionAvailable(boolean RA) {
        this.RA = RA;
    }

    public boolean isRecursionDesired() {
        return RD;
    }

    public void setRecursionDesired(boolean RD) {
        this.RD = RD;
    }

    public boolean isTruncated() {
        return TC;
    }

    public void setTruncated(boolean TC) {
        this.TC = TC;
    }

    public boolean isAuthoritativeAnswer() {
        return AA;
    }

    public void setAuthoritativeAnswer(boolean AA) {
        this.AA = AA;
    }

    public boolean isQueryResponse() {
        return QR;
    }

    public void setQueryResponse(boolean QR) {
        this.QR = QR;
    }

    public RCode getRCode() {
        return rCode;
    }

    public void setRCode(RCode rCode) {
        this.rCode = rCode;
    }

    public OPCode getOpCode() {
        return opCode;
    }

    public void setOpCode(OPCode opCode) {
        this.opCode = opCode;
    }

    public short getFlags() {
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
