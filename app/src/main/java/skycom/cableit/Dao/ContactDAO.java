package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Contact;

@Dao
public interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addContact(Contact contact);

    @Query("select * from contact")
    public List<Contact> getAllContact();

    @Query("select * from contact where id = :contactID")
    public List<Contact> getContact(long contactID);

    @Query("select * from contact where companyID = :companyID")
    public List<Contact> getContactsFromCompany(long companyID);

    @Query("select * from contact c join quotecontact qc on c.id = qc.contactID where qc.quoteID = :quoteID")
    public List<Contact> getContactsFromQuote(long quoteID);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCompany(Contact contact);

    @Query("delete from contact")
    void removeAllContacts();
}