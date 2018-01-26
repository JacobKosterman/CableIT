package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Company_Address;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // This is Stetho - Used to inspect the local database when launching the app
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        database = AppDatabase.getDatabase(getApplicationContext());

        Address addressOne = new Address("Address 1", "Address 2", "Unit 1", "N2N 2N2", "ON", "CA");
        Company companyOne = new Company("Test Company");
        Company_Address company_addressOne = new Company_Address(addressOne.id, companyOne.id);

        //Add some dummy information to database for testing purposes
        database.addressDao().addAddress(addressOne);
        database.companyDao().addCompany(companyOne);

        database.companyAddressDao().addAddressCompany(company_addressOne);


        // Navigates to Add Company page
        Button btnAddCompany = findViewById(R.id.btnAddCompany);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CompanyActivity.class);
                startActivity(intent);

            }
        });


    }
}
