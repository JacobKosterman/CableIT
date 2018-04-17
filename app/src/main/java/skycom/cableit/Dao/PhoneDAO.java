package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Phone;

@Dao
public interface PhoneDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPhone(Phone phone);

    @Query("select * from phone")
    public List<Phone> getAllPhones();

    @Query("select * from phone where id = :phoneID")
    public Phone getPhone(long phoneID);

    @Query("select * from phone where contactID = :contactID")
    public List<Phone> getCompanyPhoneNumbers(long contactID);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePhone(Phone phone);
}