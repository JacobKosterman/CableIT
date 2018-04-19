package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;

/**
 * Created by Sydney on 3/26/2018.
 */
@Dao
public interface QuoteLineDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuoteLine(QuoteLine quoteLine);

    @Query("select * from quoteLine where quoteID = :quoteID")
    public List<QuoteLine> getQuoteLinesForQuote(long quoteID);

    @Query("select * from quoteLine where id = :quoteLineID")
    public List<QuoteLine> getQuoteLine(long quoteLineID);

    @Query("select * from quoteLine")
    public List<QuoteLine> getAllQuoteLines();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuoteLine(QuoteLine quoteLine);
}
