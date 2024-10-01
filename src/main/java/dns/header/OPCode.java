package dns.header;

public enum OPCode {
    STANDARD_QUERY(0),
    INVERSE_QUERY(1),
    STATUS(2);

    private final int value;

    OPCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
