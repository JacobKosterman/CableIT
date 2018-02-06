package skycom.cableit.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.CompanyAddress;

@Dao
public interface CompanyAddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAddressCompany(CompanyAddress companyAddress);

    @Query("select * from companyAddress where addressId = :AddressId AND companyId = :CompanyId")
    public List<CompanyAddress> getAddressCompanyInfos(long AddressId, long CompanyId);

    //@Query("select * from address")
    //public List<Address> getAllAddressCompanyInfos();

    //@Update(onConflict = OnConflictStrategy.REPLACE)
    //void updateAddressCompanyInfo(Address address);

    //@Query("delete from address")
    //void removeAllAddressCompanyInfo();
}
