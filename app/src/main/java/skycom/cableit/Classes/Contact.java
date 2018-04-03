package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int companyID;
    public int phoneID;
    public String contactType;
    public String contactName;
    public String emailAddress;
    public boolean isActive;


    public Contact(int companyID, int phoneID, String contactType, String contactName, String emailAddress, boolean isActive) {
        this.companyID = companyID;
        this.phoneID = phoneID;
        this.contactType = contactType;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
        this.isActive = isActive;

    }

}
