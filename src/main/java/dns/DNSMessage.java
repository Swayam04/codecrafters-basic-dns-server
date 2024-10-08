package dns;

import dns.header.DNSHeader;
import dns.header.OPCode;
import dns.header.RCode;

public class DNSMessage {

    private final byte[] message = new byte[512];
    private final DNSHeader header;

    private DNSMessage(Builder builder) {
        this.header = builder.header;
    }

    public static class Builder {
        private final DNSHeader header;

        public Builder() {
            this.header = new DNSHeader();
        }

        public Builder setID(short ID) {
            this.header.setID(ID);
            return this;
        }

        public Builder setQueryResponse(boolean QR) {
            this.header.setQR(QR);
            return this;
        }

        public Builder setQuestionCount(short QDCOUNT) {
            this.header.setQuestionCount(QDCOUNT);
            return this;
        }

        public Builder setAnswerCount(short ANCOUNT) {
            this.header.setAnswerCount(ANCOUNT);
            return this;
        }

        public Builder setNameServerCount(short NSCOUNT) {
            this.header.setNameServerCount(NSCOUNT);
            return this;
        }

        public Builder setAdditionalRecordCount(short ARCOUNT) {
            this.header.setAdditionalRecordCount(ARCOUNT);
            return this;
        }

        public Builder setRecursionDesired(boolean RD) {
            this.header.setRD(RD);
            return this;
        }

        public Builder setRecursionAvailable(boolean RA) {
            this.header.setRA(RA);
            return this;
        }

        public Builder setOpCode(OPCode opCode) {
            this.header.setOpCode(opCode);
            return this;
        }

        public Builder setRCode(RCode rCode) {
            this.header.setRCode(rCode);
            return this;
        }

        public Builder setTruncatedMessage(boolean TC) {
            this.header.setTC(TC);
            return this;
        }

        public DNSMessage build() {
            return new DNSMessage(this);
        }
    }

    public byte[] getMessage() {
        byte[] dnsHeader = header.buildDnsHeader();
        System.arraycopy(dnsHeader, 0, message, 0, dnsHeader.length);
        return message;
    }

    public short getID() {
        return header.getID();
    }

    public short getQuestionCount() {
        return header.getQuestionCount();
    }

    public short getAnswerCount() {
        return header.getAnswerCount();
    }

    public short getNameServerCount() {
        return header.getNameServerCount();
    }

    public short getAdditionalRecordCount() {
        return header.getAdditionalRecordCount();
    }

    public boolean isQueryResponse() {
        return header.isQR();
    }

    public boolean isAuthoritativeAnswer() {
        return header.isAA();
    }

    public boolean isRecursionDesired() {
        return header.isRD();
    }

    public boolean isRecursionAvailable() {
        return header.isRA();
    }

    public boolean isTruncatedMessage() {
        return header.isTC();
    }

    public OPCode getOpCode() {
        return header.getOpCode();
    }

    public RCode getRCode() {
        return header.getRCode();
    }
}

