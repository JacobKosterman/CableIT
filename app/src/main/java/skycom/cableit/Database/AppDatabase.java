package skycom.cableit.Database;


import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Company_Address;
import skycom.cableit.Dao.CompanyDao;
import skycom.cableit.Dao.AddressDao;
import skycom.cableit.Dao.Company_AddressDao;

@Database(entities = {Company.class, Address.class, Company_Address.class}, version = 16, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract CompanyDao companyDao();
    public abstract AddressDao addressDao();
    public abstract Company_AddressDao companyAddressDao();


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