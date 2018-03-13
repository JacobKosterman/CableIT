package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Product;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Product product);

    @Query("select * from product")
    public List<Product> getAllProducts();
/*
    @Query("select * from product where id = :ProductID")
    public List<Product> getProduct(long AddressId);

    @Query("select * from product where companyID = :CompanyId")
    public List<Product> getAddressFromCompany(long CompanyId);



    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAddress(Product product);

    @Query("delete from product")
    void removeAllProducts();*/
}
