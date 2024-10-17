package dns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            for(String label : domainName.split("\\.")) {
                byteArrayOutputStream.write((byte) label.length());;
                byteArrayOutputStream.write(label.getBytes(StandardCharsets.UTF_8));
            }
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(ByteBuffer.allocate(4)
                    .putShort(recordType.getValue())
                    .putShort(dnsClass.getValue())
                    .array());
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Error building DNS question", e);
        }
    }
}
