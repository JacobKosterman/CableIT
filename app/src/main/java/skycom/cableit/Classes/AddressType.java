package skycom.cableit.Classes;

import java.util.HashMap;
import java.util.Map;

public enum AddressType {

    BILLING(1),
    SITE(2);

    private int value;
    private static Map map = new HashMap<>();

    private AddressType(int value) {
        this.value = value;
    }

    static {
        for (AddressType addressType : AddressType.values()) {
            map.put(addressType.value, addressType);
        }
    }

    public static AddressType valueOf(int pageType) {
        return (AddressType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }

}
