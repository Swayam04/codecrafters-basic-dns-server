package utils;

import dns.*;
import dns.header.DNSHeader;
import dns.header.OPCode;
import dns.header.RCode;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DNSMessageParser {
    private final ByteBuffer byteBuffer;
    private final Map<Integer, String> domainNameCache;

    public DNSMessageParser(byte[] data) {
        this.byteBuffer = ByteBuffer.wrap(data);
        this.domainNameCache = new HashMap<>();
    }

    public DNSMessage parseMessage() {
        DNSHeader header = parseHeader();
        DNSMessage dnsMessage = new DNSMessage(header);

        for(int i = 0; i < header.getQuestionCount(); i++) {
            dnsMessage.addQuestion(parseQuestion());
        }

        for(int i = 0; i < header.getAnswerCount(); i++) {
            dnsMessage.addAnswer(parseRecord());
        }
        return dnsMessage;
    }

    private DNSHeader parseHeader() {
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

    private DNSQuestion parseQuestion() {
        String domainName = parseDomainName();
        DNSClass dnsClass = DNSClass.fromValue(byteBuffer.getShort());
        DNSType dnsType = DNSType.fromValue(byteBuffer.getShort());
        return new DNSQuestion(domainName, dnsType, dnsClass);
    }


    private DNSRecord parseRecord() {
        String domainName = parseDomainName();
        DNSClass dnsClass = DNSClass.fromValue(byteBuffer.getShort());
        DNSType dnsType = DNSType.fromValue(byteBuffer.getShort());
        long timeToLive = byteBuffer.getInt() & 0xFFFFFFFFL;
        int rdLength = byteBuffer.getShort() & 0xFFFF;
        String rData = parseARecordData();

        return new DNSRecord(domainName, dnsType, dnsClass, timeToLive, rdLength, rData);
    }

    private String parseDomainName() {
        StringBuilder domainName = new StringBuilder();
        int position = byteBuffer.position();
        boolean isPointer = false;
        while (true) {
            int length = byteBuffer.get() & 0xFF;
            if ((length & 0xC0) == 0xC0) {
                int pointerOffset = ((length & 0x3F) << 8) | (byteBuffer.get() & 0xFF);
                String cachedDomain = domainNameCache.get(pointerOffset);
                if (cachedDomain != null) {
                    domainName.append(".");
                    domainName.append(cachedDomain);
                }
                isPointer = true;
                break;
            } else if (length == 0) {
                break;
            } else {
                byte[] labelBytes = new byte[length];
                byteBuffer.get(labelBytes);

                if (!domainName.isEmpty()) {
                    domainName.append(".");
                }
                domainName.append(new String(labelBytes, StandardCharsets.UTF_8));
            }
        }
        if (!isPointer) {
            domainNameCache.putIfAbsent(position, domainName.toString());
        }
        return domainName.toString();
    }

    private String parseARecordData() {
        return String.format("%d.%d.%d.%d",
                byteBuffer.get() & 0xFF,
                byteBuffer.get() & 0xFF,
                byteBuffer.get() & 0xFF,
                byteBuffer.get() & 0xFF);
    }

    private boolean parseQR(short flag) {
        return (flag & (1 << 15)) != 0;
    }

    private OPCode parseOPCode(short flag) {
        int opCode = (flag & (15 << 11)) >> 11;
        return OPCode.fromValue(opCode);
    }

    private boolean parseAA(short flag) {
        return (flag & (1 << 10)) != 0;
    }

    private boolean parseTC(short flag) {
        return (flag & (1 << 9)) != 0;
    }

    private boolean parseRD(short flag) {
        return (flag & (1 << 8)) != 0;
    }

    private boolean parseRA(short flag) {
        return (flag & (1 << 7)) != 0;
    }

    private RCode parseRCode(short flag) {
        int rCode = (flag & 15);
        return RCode.fromValue(rCode);
    }

}
