package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product {

        @PrimaryKey(autoGenerate = true)
        public int id;
        public int productCategoryID;
        public String productName;
        public String partNo;
        public String description;
        public Double productCost;
        public Double markupRate;
        public Double markupAmount;
        public Boolean isActive;


        public Product(int productCategoryID, String productName, String partNo, String description,
                       Double productCost, Double markupRate, Double markupAmount, Boolean isActive ) {
            this.productCategoryID = productCategoryID;
            this.productName = productName;
            this.partNo = partNo;
            this.description = description;
            this.productCost = productCost;
            this.markupRate = markupRate;
            this.markupAmount = markupAmount;
            this.isActive = isActive;
        }


}
