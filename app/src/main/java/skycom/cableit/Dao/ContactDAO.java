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

    @Query("select * from contact where id = :contactId")
    public List<Contact> getContact(long contactId);

    @Query("select * from contact where companyID = :companyId")
    public List<Contact> getContactsFromCompany(long companyId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCompany(Contact contact);

    @Query("delete from contact")
    void removeAllContacts();
}