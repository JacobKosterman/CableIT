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

    @Query("select * from address where id = :AddressId")
    public List<Address> getAddress(long AddressId);

    @Query("select * from address where companyID = :CompanyId")
    public List<Address> getAddressFromCompany(long CompanyId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAddress(Address address);

    @Query("delete from address")
    void removeAllAddress();
}
