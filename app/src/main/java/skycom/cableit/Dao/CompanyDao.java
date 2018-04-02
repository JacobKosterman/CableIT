package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Company;

@Dao
public interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCompany(Company company);

    @Query("select * from company")
    public List<Company> getAllCompany();

    @Query("select * from company where name LIKE :companyName")
    public List<Company> getCompanyByName(String companyName);

    @Query("select * from company where id = :companyId")
    public List<Company> getCompany(long companyId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCompany(Company company);

    @Query("delete from company")
    void removeAllCompanies();
}