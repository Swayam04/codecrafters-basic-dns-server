package dns;

public enum DNSType {
    A(1),
    NS(2),
    CNAME( 5),
    SOA(6),
    PTR(12),
    HINFO(13),
    MX(15),
    TXT(16),
    AAAA(28),
    AXFR(252),
    ALL(255);

    private final int value;

    DNSType(int value) {
        this.value = value;
    }

    public short getValue() {
        return (short) value;
    }
}
