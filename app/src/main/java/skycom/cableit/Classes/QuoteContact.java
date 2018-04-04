package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class QuoteContact {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int contactID;
    public int quoteID;

    public QuoteContact(int contactID, int quoteID){

        this.contactID = contactID;
        this.quoteID = quoteID;

    }


}
