package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.ProductCategory;

@Dao
public interface ProductCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProductCategory(ProductCategory productCategory);

    @Query("select * from productCategory")
    public List<ProductCategory> getAllProductCategories();

    @Query("select * from ProductCategory where id = :productCategoryID")
    public List<ProductCategory> getProductCategory(long productCategoryID);

    @Query("select * from ProductCategory where categoryName LIKE :newField")
    public List<ProductCategory> checkIfExists(String newField);

    @Query("select * from ProductCategory where categoryName LIKE :proCatName")
    public ProductCategory getProductCategoryByName(String proCatName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAddress(Address address);

    @Query("delete from address")
    void removeAllAddress();
}