package utils;

import dns.DNSMessage;
import dns.header.DNSHeader;
import dns.header.OPCode;
import dns.header.RCode;

import java.nio.ByteBuffer;

public class DNSMessageParser {

    public static DNSMessage parseMessage(byte[] message) {
        DNSHeader header = parseHeader(message);
        return new DNSMessage(header);
    }

    private static DNSHeader parseHeader(byte[] message) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(message, 0, 12);
        DNSHeader header = new DNSHeader();
        short id, flags, qdCount, anCount, nsCount, arCount;
        id = byteBuffer.getShort();
        flags = byteBuffer.getShort();
        qdCount = byteBuffer.getShort();
        anCount = byteBuffer.getShort();
        nsCount = byteBuffer.getShort();
        arCount = byteBuffer.getShort();

        header.setID(id);
        header.setQR(parseQR(flags));
        header.setOpCode(parseOPCode(flags));
        header.setAA(parseAA(flags));
        header.setTC(parseTC(flags));
        header.setRD(parseRD(flags));
        header.setRA(parseRA(flags));
        header.setRCode(parseRCode(flags));
        header.setQuestionCount(qdCount);
        header.setAnswerCount(anCount);
        header.setNameServerCount(nsCount);
        header.setAdditionalRecordCount(arCount);

        return header;
    }

    private static boolean parseQR(short flag) {
        return (flag & (1 << 15)) != 0;
    }

    private static OPCode parseOPCode(short flag) {
        int opCode = (flag & (15 << 11)) >> 11;
        return OPCode.fromValue(opCode);
    }

    private static boolean parseAA(short flag) {
        return (flag & (1 << 10)) != 0;
    }

    private static boolean parseTC(short flag) {
        return (flag & (1 << 9)) != 0;
    }

    private static boolean parseRD(short flag) {
        return (flag & (1 << 8)) != 0;
    }

    private static boolean parseRA(short flag) {
        return (flag & (1 << 7)) != 0;
    }

    private static RCode parseRCode(short flag) {
        int rCode = (flag & 15);
        return RCode.fromValue(rCode);
    }

}
