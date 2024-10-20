package dns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DNSRecord {

    private final String domainName;
    private final DNSType dnsType;
    private final DNSClass dnsClass;
    private final long ttl;
    private final int rdLength;
    private final String rData;

    public DNSRecord(String domainName, DNSType dnsType, DNSClass dnsClass, long ttl, int rdLength, String rData) {
        this.domainName = domainName;
        this.dnsType = dnsType;
        this.dnsClass = dnsClass;
        this.ttl = ttl;
        this.rdLength = rdLength;
        this.rData = rData;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getRData() {
        return rData;
    }

    public int getRdLength() {
        return rdLength;
    }

    public long getTtl() {
        return ttl;
    }

    public DNSClass getDnsClass() {
        return dnsClass;
    }

    public DNSType getDnsType() {
        return dnsType;
    }

    public byte[] buildDNSRecord() {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            for(String label : domainName.split("\\.")) {
                byteArrayOutputStream.write((short) label.length());
                byteArrayOutputStream.write(label.getBytes(StandardCharsets.UTF_8));
            }
            byteArrayOutputStream.write(0);

            byteArrayOutputStream.write(ByteBuffer.allocate(10)
                    .putShort(dnsType.getValue())
                    .putShort(dnsClass.getValue())
                    .putInt((int) ttl)
                    .putShort((short) rdLength)
                    .array());

            for(String ipLabel : rData.split("\\.")) {
                byte ipSection = (byte) Integer.parseInt(ipLabel);
                byteArrayOutputStream.write(ipSection);
            }
            return byteArrayOutputStream.toByteArray();
        } catch(IOException e) {
            throw new UncheckedIOException("Error building DNS record", e);
        }
    }
}
