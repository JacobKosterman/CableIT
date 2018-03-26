package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.ProductCategory;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Setting Up Stetho on startup
        //
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        database = AppDatabase.getDatabase(getApplicationContext());

        addDummyData();
        //importFromCSV();

        //
        // Navigates to Add Company page
        //
        Button btnAddCompany = findViewById(R.id.btnCompanies);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                startActivity(intent);
            }
        });

        //
        // Navigate to Product List Page
        //
        Button btnProductList = findViewById(R.id.btnProductList);
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        //This removes the functionality of back button on main screen
    }

    private void importFromCSV() {

        //Check to see if that products data has already been loaded.
        List<Product> products = database.productDao().getAllProducts();
        if (products.isEmpty()) {

            InputStream is = getResources().openRawResource(R.raw.product_list);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );

            String line = "";

            try {
                while ((line = reader.readLine()) != null) {

                    String[] tokens = line.split("\\|");

                    ProductCategory tempProCat;
                    String tempProNo = "";
                    String tempProName = "";
                    String tempDescription = "";
                    int tempCatId = 0;
                    double tempPrice;
                    double tempMarkUpRate;
                    double tempMarkUpAmount;

                    if (tokens[0].length() > 0) {
                        tempProNo = tokens[0];
                    } else {
                        tempProNo = "";
                    }

                    //Add Product Category is it does NOT exist
                    if (tokens[1].length() > 0 ) {
                        List<ProductCategory> listProductCategories = database.productCategoryDao().checkIfExists(tokens[1].toString());
                        tempProCat = new ProductCategory(tokens[1], 0.67);
                        if (listProductCategories.isEmpty()) {
                            database.productCategoryDao().addProductCategory(tempProCat);
                            //Getting from database gets the active ID number.
                            tempProCat = database.productCategoryDao().getProductCategoryByName(tempProCat.categoryName);
                            tempCatId = tempProCat.id;
                        }
                        else{
                            tempCatId = listProductCategories.get(0).id;
                        }
                    }

                    if (tokens[2].length() > 0) {
                        tempProName = tokens[2];
                    } else {
                        tempProNo = "";
                    }

                    if (tokens[3].length() > 0) {
                        tempDescription = tokens[3];
                    } else {
                        tempDescription = "";
                    }

                    if (tokens[4].length() > 0) {
                        tempPrice = Double.parseDouble(tokens[4].toString());
                    } else {
                        tempPrice = 0.0;
                    }

                    if (tokens[5].length() > 0) {
                        tempMarkUpRate = Double.parseDouble(tokens[5].toString());
                    } else {
                        tempMarkUpRate = 0.0;
                    }

                    if (tokens[6].length() > 0) {
                        tempMarkUpAmount = Double.parseDouble(tokens[6].toString());
                    } else {
                        tempMarkUpAmount = 0.0;
                    }


//                    if (tokens.length >= 7 && tokens[2].length() > 0) {
//                        tempProName = tokens[1];
//                    } else {
//                        tempProName = "";
//                    }



                    database.productDao().addProduct(new Product(tempCatId,
                            tempProNo,
                            tempProName,
                            tempDescription,
                            tempPrice,
                            tempMarkUpRate,
                            tempMarkUpAmount,
                            true));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDummyData(){
        List<Company> companies = database.companyDao().getAllCompany();
        if (companies.isEmpty()) {

            database.addressDao().addAddress(new Address( 1,"Address 1", "Address 2", "Kitchener","N2N 2N2", "ON", "CA", Boolean.FALSE ));
            database.companyDao().addCompany(new Company("Test Company 1", "This is a description"));
            database.companyDao().addCompany(new Company("Test Company 2", "This is a description"));
            database.companyDao().addCompany(new Company("Test Company 3", "This is a description"));
            database.companyDao().addCompany(new Company("Test Company 4", "This is a description"));
            database.companyDao().addCompany(new Company("Test Company 5", "This is a description"));
            database.companyDao().addCompany(new Company("Test Company 6", "This is a description"));

            List<Address> addressOne = database.addressDao().getAddress(1);
            List<Company> companyOne = database.companyDao().getCompany(1);

        }
    }
}
