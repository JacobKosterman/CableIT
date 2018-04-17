package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class QuoteLine {


    @PrimaryKey(autoGenerate = true)
    public int id;
    public int quoteID;
    public int productID;
    public String lineComment;
    public Double quantity;
    public Double productCost;
    public Double markupRate;
    public Double markupAmount;



    public QuoteLine(int quoteID, int productID, String lineComment, Double quantity, Double productCost,
                     Double markupRate, Double markupAmount ){

        this.quoteID = quoteID;
        this.productID = productID;
        this.lineComment = lineComment;
        this.quantity = quantity;
        this.productCost = productCost;
        this.markupRate = markupRate;
        this.markupAmount = markupAmount;

    }

}
