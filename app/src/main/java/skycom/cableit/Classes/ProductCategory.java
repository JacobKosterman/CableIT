package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProductCategory {


    @PrimaryKey(autoGenerate = true)
    public int id;
    public String categoryName;
    public Double markupRate;



    public ProductCategory(String categoryName, Double markupRate){
        this.categoryName = categoryName;
        this.markupRate = markupRate;

    }

}
