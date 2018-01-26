package skycom.cableit.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company_Address;

@Dao
public interface Company_AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAddressCompany(Company_Address company_address);

    @Query("select * from company_address where addressId = :AddressId AND companyId = :CompanyId")
    public List<Company_Address> getAddressCompanyInfos(long AddressId, long CompanyId);

    //@Query("select * from address")
    //public List<Address> getAllAddressCompanyInfos();

    //@Update(onConflict = OnConflictStrategy.REPLACE)
    //void updateAddressCompanyInfo(Address address);

    //@Query("delete from address")
    //void removeAllAddressCompanyInfo();
}
