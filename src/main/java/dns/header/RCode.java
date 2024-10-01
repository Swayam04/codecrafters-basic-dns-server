package dns.header;

public enum RCode {

    NO_ERROR(0),
    FORMAT_ERROR(1),
    SERVER_FAILURE(2),
    NAME_ERROR(3),
    NOT_IMPLEMENTED(4),
    REFUSED(5);

    private final int value;
    RCode(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
