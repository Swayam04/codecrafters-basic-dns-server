package dns.header;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DNSQuestion {

    private final String domainName;
    private final DNSType recordType;
    private final DNSClass dnsClass;

    public DNSQuestion(String domainName, DNSType recordType, DNSClass dnsClass) {
        this.domainName = domainName;
        this.recordType = recordType;
        this.dnsClass = dnsClass;
    }

    public DNSType getRecordType() {
        return recordType;
    }

    public String getDomainName() {
        return domainName;
    }

    public DNSClass getDnsClass() {
        return dnsClass;
    }

    public byte[] buildDNSQuestion() {
        String[] labels = domainName.split("\\.");
        List<Byte> encodedNameList = new ArrayList<>();

        for(String label : labels) {
            byte length = (byte) label.length();
            encodedNameList.add(length);
            byte[] labelBytes = label.getBytes();
            for(byte b : labelBytes) {
                encodedNameList.add(b);
            }
        }
        encodedNameList.add((byte) 0);

        byte[] encodedName = new byte[encodedNameList.size()];
        for(int i = 0; i < encodedNameList.size(); i++) {
            encodedName[i] = encodedNameList.get(i);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(encodedName.length + 4);
        byteBuffer.put(encodedName);
        byteBuffer.putShort(recordType.getValue());
        byteBuffer.putShort(dnsClass.getValue());
        return byteBuffer.array();
    }
}
