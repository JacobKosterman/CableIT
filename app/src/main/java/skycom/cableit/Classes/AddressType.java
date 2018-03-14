package skycom.cableit.Classes;

public enum AddressType {

        Billing("Billing"),
        Site("Site");

        private String addressType;

        private AddressType(String addressType){
            this.addressType = addressType;
        }

        @Override public String toString(){
            return addressType;
        }

}
