package skycom.cableit.Classes;

public enum ContactType {

    Billing("Billing"),
    Maintenance("Maintenance"),
    Supervisor("Site");

    private String contactType;

    private ContactType(String contactType){
        this.contactType = contactType;
    }

    @Override public String toString(){
        return contactType;
    }

}
