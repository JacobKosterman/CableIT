package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String StreetAddress1;
    public String StreetAddress2;
    public String UnitNumber;
    public String PostalCode;
    public String Province;
    public String Country;


    public Address(String StreetAddress1, String StreetAddress2, String UnitNumber, String PostalCode,
                   String Province, String Country) {
        this.StreetAddress1 = StreetAddress1;
        this.StreetAddress2 = StreetAddress2;
        this.UnitNumber = UnitNumber;
        this.PostalCode = PostalCode;
        this.Province = Province;
        this.Country = Country;

    }
}