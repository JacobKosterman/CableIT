package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;


@Entity
public class Quote {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String quoteNumber;
    public int companyID;
    public int siteAddressID;
    public int billingAddressID;
    @TypeConverters({TimestampConverter.class})
    public Date dateCreated;
    @TypeConverters({TimestampConverter.class})
    public Date dateUpdated;




    public Quote(String quoteNumber, int companyID, int siteAddressID, int billingAddressID, Date dateCreated, Date dateUpdated ){

        this.quoteNumber = quoteNumber;
        this.companyID = companyID;
        this.siteAddressID = siteAddressID;
        this.billingAddressID = billingAddressID;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

}
