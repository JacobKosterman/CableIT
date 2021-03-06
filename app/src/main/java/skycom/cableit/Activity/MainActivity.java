package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.Phone;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.ProductCategory;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //
        // Setting Up Stetho on startup
        //
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        database = AppDatabase.getDatabase(getApplicationContext());

        addDummyData();
        importFromCSV();

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

        //
        // Navigate to Quotes List Page
        //
        Button btnQuoteList = findViewById(R.id.btnQuoteList);
        btnQuoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        //This removes the functionality of back button on main screen
    }

    private void importFromCSV() {

        //Check to see if that products data has already been loaded.
        List<Product> products = database.productDAO().getAllProducts();
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
                    if (tokens[1].length() > 0) {
                        List<ProductCategory> listProductCategories = database.productCategoryDAO().checkIfExists(tokens[1].toString());
                        tempProCat = new ProductCategory(tokens[1], 0.67);
                        if (listProductCategories.isEmpty()) {
                            database.productCategoryDAO().addProductCategory(tempProCat);
                            //Getting from database gets the active ID number.
                            tempProCat = database.productCategoryDAO().getProductCategoryByName(tempProCat.categoryName);
                            tempCatId = tempProCat.id;
                        } else {
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


                    database.productDAO().addProduct(new Product(tempCatId,
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

    private void addDummyData() {
        List<Company> companies = database.companyDAO().getAllCompany();
        if (companies.isEmpty()) {

            database.addressDAO().addAddress(new Address(1, 1, "99 Foxglove Cres", "Address 2", "Kitchener", "N2E 1H8", "ON", "CA", Boolean.TRUE));
            database.addressDAO().addAddress(new Address(1, 2, "200 Doon Valley Drive", "", "Kitchener", "N2G 4M4", "ON", "CA", Boolean.TRUE));
            database.addressDAO().addAddress(new Address(1, 2, "Pearson International Airport", "Terminal 1", "Mississauga", "MississaugaL5P 1B2", "ON", "CA", Boolean.TRUE));

            database.companyDAO().addCompany(new Company("Company ABC", "Customer Service Company"));
            database.companyDAO().addCompany(new Company("Company DEF", "Construction Consulting"));
            database.companyDAO().addCompany(new Company("Company GHI", "Engineering Firm"));
            database.companyDAO().addCompany(new Company("Company JKL", "Contracting Company"));
            database.companyDAO().addCompany(new Company("Company MNO", "Tech Firm"));
            database.companyDAO().addCompany(new Company("Company PQR", "Warehouse"));
            database.companyDAO().addCompany(new Company("Company STU", "Supplier"));
            database.companyDAO().addCompany(new Company("Company VWX", "Service Company"));
            database.companyDAO().addCompany(new Company("Company YZ", "Accounting Firm"));
            database.companyDAO().addCompany(new Company("Company 123", "Art Studio"));
            database.contactDAO().addContact(new Contact(1, 1, "Bob Suruncle", "bob@email.com", Boolean.TRUE));
            database.contactDAO().addContact(new Contact(1, 2, "Jake Jacobs", "jjacobs@email.com", Boolean.TRUE));
            database.phoneDAO().addPhone(new Phone(1, "888-888-8888", "123", "Reception Desk"));


            database.quoteDAO().addQuote(new Quote("2018040401", 1, 1, 1, new Date(), new Date()));


            database.quoteLineDAO().addQuoteLine(new QuoteLine(1, 1, "Initial Visit Quote", 2.0, 3.0, 4.0, 4.0));


            List<Address> addressOne = database.addressDAO().getAddress(1);
            List<Company> companyOne = database.companyDAO().getCompany(1);

        }
    }
}
