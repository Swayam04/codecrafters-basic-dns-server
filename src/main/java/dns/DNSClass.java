package dns;

public enum DNSClass {
    IN(1),
    CS(2),
    CH(3),
    HS(4),
    ALL(255);

    private final int value;

    DNSClass(int value) {
        this.value = value;
    }

    public short getValue() {
        return (short) value;
    }
}
