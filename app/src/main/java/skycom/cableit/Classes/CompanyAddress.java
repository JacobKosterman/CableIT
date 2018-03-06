package skycom.cableit.Classes;
//dnu
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "CompanyAddress",
    foreignKeys = {
        @ForeignKey(
            entity = Address.class,
            parentColumns = "id",
            childColumns = "addressId",
            onDelete = ForeignKey.CASCADE
    ), @ForeignKey(
            entity = Company.class,
            parentColumns =  "id",
            childColumns = "companyId",
            onDelete = ForeignKey.CASCADE

    )}
)


public class CompanyAddress {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public final long addressId;
    public final long companyId;

    public CompanyAddress(long addressId, long companyId) {
        this.addressId = addressId;
        this.companyId = companyId;

    }
}