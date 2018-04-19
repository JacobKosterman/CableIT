package skycom.cableit.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteEditActivity extends AppCompatActivity {

    private AppDatabase database;
    Context context = this;
    public int companyID;
    public Date dateCreated;
    public Date dateUpdated;
    Quote quote;
    Company company;
    Address siteAddress;
    Address billingAddress;
    List<Address> addresses = new ArrayList<>();
    List<Company> companies = new ArrayList<>();
    boolean assigning;

    EditText txtQuoteNumber;
    Spinner spnCompanyID;
    Spinner spnBillingAddressID;
    Spinner spnSiteAddressID;
    Button btnEditQuote;

    ArrayAdapter companyAdapter;
    ArrayAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_new);
        database = AppDatabase.getDatabase(getApplicationContext());
        String quoteStart = new SimpleDateFormat("YYYYMMdd").format(new Date());

        //BEGIN: Start Logic
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                quote = database.quoteDAO().getQuote(extras.getInt("QUOTE_ID")).get(0);
            }
        } else {
            quote = database.quoteDAO().getQuote((int) savedInstanceState.getSerializable("QUOTE_ID")).get(0);
        }

        if (quote != null) {
            //Gather objects
            company = database.companyDAO().getCompany(quote.companyID).get(0);
            siteAddress = database.addressDAO().getAddress(quote.siteAddressID).get(0);
            billingAddress = database.addressDAO().getAddress(quote.billingAddressID).get(0);
        } else {
            //make toast and return to quote list
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
            startActivity(intent);
            return;
        }

        // Declare Display Objects
        txtQuoteNumber = findViewById(R.id.txtQuoteNumber);
        spnCompanyID = findViewById(R.id.spnCompanyID);
        spnBillingAddressID = findViewById(R.id.spnBillingAddressID);
        spnSiteAddressID = findViewById(R.id.spnSiteAddressID);
        btnEditQuote = findViewById(R.id.btnEditQuote);
        txtQuoteNumber.setText(quote.quoteNumber);
        txtQuoteNumber.setInputType(InputType.TYPE_NULL);

        //Prepare Lists
        companies = database.companyDAO().getAllCompany();

        //Prepare adapters
        companyAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, companies);
        addressAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, addresses);
        spnCompanyID.setAdapter(companyAdapter);

        //Listeners
        spnCompanyID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                company = (Company) spnCompanyID.getItemAtPosition(position);
                siteAddress = null;
                billingAddress = null;
                addresses = database.addressDAO().getAddressFromCompany(company.id);
                addressAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, addresses);

                spnBillingAddressID.setAdapter(addressAdapter);
                spnSiteAddressID.setAdapter(addressAdapter);

                if (assigning) {
                    spnBillingAddressID.setSelection(addressAdapter.getPosition(billingAddress));
                    spnSiteAddressID.setSelection(addressAdapter.getPosition(siteAddress));
                    assigning = false;
                }
                //set text field
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnEditQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on save here
                dateCreated = new Date();
                dateUpdated = new Date();
                billingAddress = (Address) spnBillingAddressID.getSelectedItem();
                siteAddress = (Address) spnSiteAddressID.getSelectedItem();
                company = (Company) spnCompanyID.getSelectedItem();

                if (company != null && siteAddress != null && billingAddress != null) {
                    //call update
                    quote.billingAddressID = billingAddress.id;
                    quote.companyID = company.id;
                    quote.siteAddressID = siteAddress.id;
                    database.quoteDAO().updateQuote(quote);

                    Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                    intent.putExtra("QUOTE_ID", quote.id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a Company, Billing Address, and Site Address.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        assigning = true;
        spnCompanyID.setSelection(companyAdapter.getPosition(company));
    }
}
