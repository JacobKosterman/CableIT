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

public class QuoteNewActivity extends AppCompatActivity {

    private AppDatabase database;
    Context context = this;
    public String quoteNumber;
    public int companyID;
    public int siteAddressID;
    public int billingAddressID;
    public Date dateCreated;
    public Date dateUpdated;
    Company company;
    Address siteAddress;
    Address billingAddress;
    List<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_new);
        database = AppDatabase.getDatabase(getApplicationContext());
        String quoteStart = new SimpleDateFormat("YYYYMMdd").format(new Date());
        List<Quote> existingQuotes = database.quoteDAO().getQuotesFromNumber(quoteStart + "%");
        int num = existingQuotes.size() + 1;


        Boolean found = false;

        while (!found) {
            quoteNumber = quoteStart + String.format("%02d", num);
            List<Quote> tempQuotes = database.quoteDAO().getQuotesFromNumber(quoteNumber);
            found = (tempQuotes.size() == 0);
            num++;
        }

        final EditText txtQuoteNumber = findViewById(R.id.txtQuoteNumber);
        final Spinner spnCompanyID =  findViewById(R.id.spnCompanyID);
        final Spinner spnBillingAddressID =  findViewById(R.id.spnBillingAddressID);
        final Spinner spnSiteAddressID =  findViewById(R.id.spnSiteAddressID);
        List<Company> companies = database.companyDAO().getAllCompany();


        ArrayAdapter companyAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, companies);

        spnCompanyID.setAdapter(companyAdapter);

        spnCompanyID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                company = (Company) spnCompanyID.getItemAtPosition(position);
                siteAddress = null;
                billingAddress = null;
                addresses = database.addressDAO().getAddressFromCompany(company.id);
                ArrayAdapter addressAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, addresses);

                spnBillingAddressID.setAdapter(addressAdapter);
                spnSiteAddressID.setAdapter(addressAdapter);
                //set text field
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        txtQuoteNumber.setText(quoteNumber);
        txtQuoteNumber.setInputType(InputType.TYPE_NULL);

        Button btnSaveQuote = findViewById(R.id.btnSaveQuote);
        btnSaveQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on save here
                dateCreated = new Date();
                dateUpdated = new Date();
                billingAddress = (Address) spnBillingAddressID.getSelectedItem();
                siteAddress = (Address) spnSiteAddressID.getSelectedItem();
                company = (Company) spnCompanyID.getSelectedItem();

                if(company!= null && siteAddress != null && billingAddress!=null)
                {
                    List<Quote> tempQuotes = database.quoteDAO().getQuotesFromNumber(quoteNumber);
                    if (tempQuotes.size() == 0) {
                        database.quoteDAO().addQuote(new Quote(quoteNumber, company.id, siteAddress.id,
                                billingAddress.id, dateCreated, dateUpdated));

                        tempQuotes = database.quoteDAO().getQuotesFromNumber(quoteNumber);

                        Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                        intent.putExtra("QUOTE_ID", tempQuotes.get(0).id);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Quote Number", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "Please select a Company, Billing Address, and Site Address.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
