package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import skycom.cableit.Classes.Quote;

/**
 * Created by Sydney on 3/26/2018.
 */
@Dao
public interface QuoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuote(Quote quote);

    @Query("select * from quote")
    public List<Quote> getAllQuotes();
}
