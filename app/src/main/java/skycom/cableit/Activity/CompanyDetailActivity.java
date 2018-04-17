package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skycom.cableit.Classes.Adapters.AddressAdapter;
import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Adapters.QuoteAdapter;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ExpandableListAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String companyIDString = "";
    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;


    ListView lvContact;
    private static ContactAdapter adapterContact;
    ListView lvAddress;
    private static AddressAdapter adapterAddress;
    ListView lvQuote;
    private static QuoteAdapter adapterQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        TextView txtName = (TextView)findViewById(R.id.textName);
        TextView txtDescription = (TextView)findViewById(R.id.textDescription);

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        companyID = prefs.getInt("MY_COMPANY", 0);
        companyIDString = String.valueOf(companyID);

        List <Company> companyOne = database.companyDAO().getCompany(companyID);

        txtName.setText(companyOne.get(0).name);
        txtDescription.setText(companyOne.get(0).description);

        lvContact =(ListView)findViewById(R.id.lstContact);
        final ArrayList<Contact> temp = new ArrayList<>();
        temp.addAll(database.contactDAO().getContactsFromCompany(companyID));

        adapterContact = new ContactAdapter(this,temp);

        lvContact.setAdapter(adapterContact);
        lvContact.setVisibility(View.GONE);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact c = (Contact) parent.getItemAtPosition(position);
                if (c != null)
                {
                    Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                    if (companyIDString != ""){
                        editor.putInt("MY_CONTACT", c.id);
                        editor.apply();
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnContactList = findViewById(R.id.btnContactList);
        btnContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp.size()==0){
                    Toast.makeText(getApplicationContext(), "This Company has no addresses yet!", Toast.LENGTH_SHORT).show();
                }else if(lvContact.getVisibility() == View.VISIBLE){
                    lvContact.setVisibility(View.GONE);
                }else{
                    lvContact.setVisibility(View.VISIBLE);
                }
            }
        });

        lvAddress =(ListView)findViewById(R.id.lstAddress);
        final ArrayList<Address> tempAddress = new ArrayList<>();
        tempAddress.addAll(database.addressDAO().getAddressFromCompany(companyID));

        adapterAddress = new AddressAdapter(this,tempAddress);

        lvAddress.setAdapter(adapterAddress);
        lvAddress.setVisibility(View.GONE);
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address a = (Address) parent.getItemAtPosition(position);

                    if (a != null)
                    {
                        Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                        if (companyIDString != "") {
                            editor.putInt("MY_ADDRESS", a.id);
                            editor.apply();
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "error",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            });
        Button btnAddressList = findViewById(R.id.btnAddressList);
        btnAddressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempAddress.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "This Company has no addresses yet!", Toast.LENGTH_SHORT).show();
                }else if(lvAddress.getVisibility() == View.VISIBLE){
                    lvAddress.setVisibility(View.GONE);
                }else{
                    lvAddress.setVisibility(View.VISIBLE);
                }
            }
        });

        lvQuote =(ListView)findViewById(R.id.lstQuotes);
        ArrayList<Quote> tempQuote = new ArrayList<>();
        tempQuote.addAll(database.quoteDAO().getQuotesForCompany(companyID));

        adapterQuote = new QuoteAdapter(this,tempQuote);

        lvQuote.setAdapter(adapterQuote);
        lvQuote.setVisibility(View.GONE);
        lvQuote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quote q = (Quote) parent.getItemAtPosition(position);

                if (q != null)
                {

                    if (companyIDString != "") {
//                        editor.putInt("MY_QUOTE", q.id);
//                        editor.apply();
//                        startActivity(intent);
                        Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                        intent.putExtra("QUOTE_ID", q   .id);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnQuoteList = findViewById(R.id.btnQuoteList);
        btnQuoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lvQuote.getVisibility() == View.VISIBLE){
                    lvQuote.setVisibility(View.GONE);
                }else{
                    lvQuote.setVisibility(View.VISIBLE);
                }
            }
        });

        //
        // The Section for the buttons
        //
        Button btnEditCompany = findViewById(R.id.btnEditCompany);
        btnEditCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyEditDetailActivity.class);
                startActivity(intent);
            }
        });

        Button btnAddAddress = findViewById(R.id.btnNewAddress);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressNewDetailActivity.class);
                startActivity(intent);
            }
        });

        Button btnAddContact = findViewById(R.id.btnNewContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactNewDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
    }
}
