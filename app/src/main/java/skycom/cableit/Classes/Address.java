package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int companyID;
    public String address1;
    public String address2;
    public String city;
    public String postalCode;
    public String province;
    public String country;
    public boolean isActive;


    public Address(int companyID, String address1, String address2, String City, String PostalCode,
                   String Province, String Country, boolean isActive) {
        this.companyID = companyID;
        this.address1 = address1;
        this.address2 = address2;
        this.city = City;
        this.postalCode = PostalCode;
        this.province = Province;
        this.country = Country;
        this.isActive = isActive;

    }
}