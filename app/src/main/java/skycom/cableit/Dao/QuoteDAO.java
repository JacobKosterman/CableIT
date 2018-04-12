package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import skycom.cableit.Classes.Quote;


@Dao
public interface QuoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuote(Quote quote);

    @Query("select * from quote")
    public List<Quote> getAllQuotes();

    @Query("select * from quote where id = :quoteID")
    public List<Quote> getQuote(long quoteID);

    @Query("select * from quote where companyID = :companyID")
    public List<Quote> getQuotesForCompany(long companyID);

    @Query("select * from quote where siteAddressID = :addressID")
    public List<Quote> getQuotesForAddress(long addressID);

}
