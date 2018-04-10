package skycom.cableit.Classes;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ContactAddress {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int contactID;
    public int addressID;


    public ContactAddress(int contactID, int addressID) {
        this.contactID = contactID;
        this.addressID = addressID;
    }
}