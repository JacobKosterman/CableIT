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


    public Address(int companyID, String address1, String address2, String city, String postalCode,
                   String province, String country, boolean isActive) {
        this.companyID = companyID;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
        this.isActive = isActive;

    }
}