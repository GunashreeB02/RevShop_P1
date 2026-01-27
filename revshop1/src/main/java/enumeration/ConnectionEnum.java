package enumeration;


public enum ConnectionEnum {

    URL("jdbc:oracle:thin:@localhost:1521/XEPDB1"),USERNAME("revshop1"),PASSWORD("revshop1");
    private final String value;
    private ConnectionEnum(String value)
    {
        this.value=value;
    }
    public String getValue() {
        return value;
    }

}
