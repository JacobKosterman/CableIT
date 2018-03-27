package skycom.cableit.Database;


import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.ProductCategory;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Classes.TimestampConverter;
import skycom.cableit.Dao.CompanyDao;
import skycom.cableit.Dao.AddressDao;
import skycom.cableit.Dao.ProductDao;
import skycom.cableit.Dao.QuoteDAO;
import skycom.cableit.Dao.QuoteLineDAO;
import skycom.cableit.Dao.ProductCategoryDao;


@Database(entities = {Company.class, Address.class, Product.class, ProductCategory.class, Contact.class, Quote.class, QuoteLine.class }, version = 17, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract CompanyDao companyDao();
    public abstract AddressDao addressDao();
    public abstract ProductDao productDao();
    public abstract ProductCategoryDao productCategoryDao();
    public abstract QuoteDAO quoteDAO();
    public abstract QuoteLineDAO quoteLineDAO();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "companyInfoDatabase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}