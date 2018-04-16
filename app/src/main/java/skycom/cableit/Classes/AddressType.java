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










    // This one was working

//        SITE("Site", 0),
//        BILLING("billing", 1);
//
//        private String stringValue;
//        private int intValue;
//        private AddressType(String toString, int value) {
//            stringValue = toString;
//            intValue = value;
//        }
//
//        @Override
//        public String toString() {
//            return stringValue;
//        }







//
//    Site(0), Billing(1);
//    private final int value;
//
//    private AddressType(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }


//    Billing("Billing"),
//    Site("Site");
//
//    private String addressType;
//
//
//
//    private AddressType(String addressType){
//        this.addressType = addressType;
//    }
//
//    @Override public String toString(){
//        return addressType;
//    }


}
