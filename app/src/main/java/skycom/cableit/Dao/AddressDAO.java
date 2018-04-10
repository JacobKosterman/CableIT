package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Address;

@Dao
public interface AddressDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAddress(Address address);

    @Query("select * from address")
    public List<Address> getAllAddresses();

    @Query("select * from address where id = :addressID")
    public List<Address> getAddress(long addressID);

    @Query("select * from address where companyID = :companyID")
    public List<Address> getAddressFromCompany(long companyID);

    @Query("select a.* from address a JOIN contactAddress ca ON ca.addressID = a.id WHERE ca.contactID = :contactID")
    public List<Address> getAddressFromContact(long contactID);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAddress(Address address);
}
