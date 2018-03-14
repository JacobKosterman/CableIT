package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int companyID;
    public String contactType;
    public String contactName;
    public String phoneNumber;
    public String phoneExt;
    public String emailAddress;
    public boolean isActive;


    public Contact(int companyID, String contactType, String contactName,
                   String phoneNumber, String phoneExt, String emailAddress, boolean isActive) {
        this.companyID = companyID;
        this.contactType = contactType;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.phoneExt = phoneExt;
        this.emailAddress = emailAddress;
        this.isActive = isActive;

    }

}
