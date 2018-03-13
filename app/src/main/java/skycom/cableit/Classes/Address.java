package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int companyID;
    public String StreetAddress1;
    public String StreetAddress2;
    public String City;
    public String PostalCode;
    public String Province;
    public String Country;
    public boolean isActive;


    public Address(int companyID, String StreetAddress1, String StreetAddress2, String City, String PostalCode,
                   String Province, String Country, boolean isActive) {
        this.companyID = companyID;
        this.StreetAddress1 = StreetAddress1;
        this.StreetAddress2 = StreetAddress2;
        this.City = City;
        this.PostalCode = PostalCode;
        this.Province = Province;
        this.Country = Country;
        this.isActive = isActive;

    }
}