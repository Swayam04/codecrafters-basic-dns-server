package dns.header;

public enum OPCode {
    STANDARD_QUERY(0),
    INVERSE_QUERY(1),
    STATUS(2),
    UNASSIGNED(3),
    NOTIFY(4),
    UPDATE(5),
    DSO(6);

    private final int value;

    OPCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OPCode fromValue(int value) {
        for (OPCode opCode : OPCode.values()) {
            if (opCode.getValue() == value) {
                return opCode;
            }
        }
        return STANDARD_QUERY;
    }
}
