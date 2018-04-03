package skycom.cableit.Classes;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ContactAddress {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int companyID;
    public int addressID;


    public ContactAddress(int companyID, int addressID) {
        this.companyID = companyID;
        this.addressID = addressID;
    }
}