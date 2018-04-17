package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.QuoteContact;

@Dao
public interface QuoteContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuoteContact(QuoteContact quoteContact);

    @Query("select * from quoteContact")
    public List<QuoteContact> getAllQuoteContacts();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuoteContact(QuoteContact quoteContact);

    @Query("delete from quoteContact where quoteID = :quoteID and  contactID = :contactID")
    void removeQuoteContact(int quoteID, int contactID);
}
