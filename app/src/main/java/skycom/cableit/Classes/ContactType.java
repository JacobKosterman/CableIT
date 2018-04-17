package skycom.cableit.Classes;

import java.util.HashMap;
import java.util.Map;

public enum ContactType {



    BILLING(1),
    MAINTENANCE(2),
    SITE(3);

    private int value;
    private static Map map = new HashMap<>();

    private ContactType(int value) {
        this.value = value;
    }

    static {
        for (ContactType contactType : ContactType.values()) {
            map.put(contactType.value, contactType);
        }
    }

    public static ContactType valueOf(int pageType) {
        return (ContactType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }





//
//
//
//    Billing("Billing"),
//    Maintenance("Maintenance"),
//    Supervisor("Site");
//
//    private String contactType;
//
//    private ContactType(String contactType){
//        this.contactType = contactType;
//    }
//
//    @Override public String toString(){
//        return contactType;
//    }

}
