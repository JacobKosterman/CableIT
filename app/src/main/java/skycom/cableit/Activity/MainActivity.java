package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.CompanyAddress;
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

        database.addressDao().addAddress(new Address( 1,"Address 1", "Address 2", "Unit 1", "N2N 2N2", "ON", "CA", Boolean.FALSE ));
        database.companyDao().addCompany(new Company("Test Company"));

        List<Address> addressOne = database.addressDao().getAddress(1);
        List<Company> companyOne = database.companyDao().getCompany(1);

        database.companyAddressDao().addAddressCompany(new CompanyAddress(addressOne.get(0).id, companyOne.get(0).id));

        // Navigates to Add Company page
        Button btnAddCompany = findViewById(R.id.btnCompanies);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CompanyActivity.class);
                startActivity(intent);

            }
        });

        Button btnAddressList = findViewById(R.id.btnAddressList);
        btnAddressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddressListActivity.class);
                startActivity(intent);

            }
        });

        Button btnProductList = findViewById(R.id.btnProductList);
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);

            }
        });


    }
}
