package skycom.cableit.Database;


import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ContactAddress;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.ProductCategory;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteContact;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Classes.TimestampConverter;
import skycom.cableit.Classes.Phone;
import skycom.cableit.Dao.AddressDAO;
import skycom.cableit.Dao.CompanyDAO;
import skycom.cableit.Dao.ContactAddressDAO;
import skycom.cableit.Dao.ProductDAO;
import skycom.cableit.Dao.QuoteContactDAO;
import skycom.cableit.Dao.QuoteDAO;
import skycom.cableit.Dao.QuoteLineDAO;
import skycom.cableit.Dao.ProductCategoryDAO;
import skycom.cableit.Dao.ContactDAO;
import skycom.cableit.Dao.PhoneDAO;


@Database(entities = {Address.class, Company.class, ContactAddress.class, Contact.class, Phone.class, Product.class, ProductCategory.class, QuoteContact.class, Quote.class, QuoteLine.class}, version = 19, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract AddressDAO addressDAO();
    public abstract CompanyDAO companyDAO();
    public abstract ContactAddressDAO contactAddressDAO();
    public abstract ContactDAO contactDAO();
    public abstract PhoneDAO phoneDAO();
    public abstract ProductCategoryDAO productCategoryDAO();
    public abstract ProductDAO productDAO();
    public abstract QuoteContactDAO quoteContactDAO();
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