package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Quote {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int quoteID;
    public int companyID;
    public int siteAddressID;
    public int billingAddressID;
    public Date dateCreated;
    public Date dateUpdated;




    public Quote(int quoteID, int companyID, int siteAddressID, int billingAddressID, Date dateCreated, Date dateUpdated ){

        this.quoteID = quoteID;
        this.companyID = companyID;
        this.siteAddressID = siteAddressID;
        this.billingAddressID = billingAddressID;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

}
