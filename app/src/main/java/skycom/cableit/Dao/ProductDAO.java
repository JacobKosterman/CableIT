package skycom.cableit.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import skycom.cableit.Classes.Product;

@Dao
public interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Product product);

    @Query("select * from product Order by productName ASC")
    public List<Product> getAllProducts();

    @Query("select * from product where partNo LIKE :productNo")
    public List<Product> getProductByNumber(String productNo);

    @Query("select * from product where id = :ProductID")
    public List<Product> getProduct(long ProductID);

}
