package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Phone {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int contactID;
    public String phoneNumber;
    public String ext;
    public String description;


    public Phone(int contactID, String phoneNumber, String ext, String description) {
        this.contactID = contactID;
        this.phoneNumber = phoneNumber;
        this.ext = ext;
        this.description = description;
    }
}