package skycom.cableit.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Adapters.QuoteLineAdapter;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteDetailActivity extends AppCompatActivity {
    Quote quote = null;
    Context context = this;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);
        database = AppDatabase.getDatabase(getApplicationContext());

        //Objects
        Address siteAddress;
        Address billingAddress;
        Company company;

        //TextViews
        final TextView txtQuoteNumber = findViewById(R.id.txtQuoteNumber);
        final TextView txtCompanyName = findViewById(R.id.txtCompanyName);
        final TextView txtSiteAddress1 = findViewById(R.id.txtSiteAddress1);
        final TextView txtSiteAddress2 = findViewById(R.id.txtSiteAddress2);
        final TextView txtSiteCityProvPostal = findViewById(R.id.txtSiteCityProvPostal);
        final TextView txtBillingAddress1 = findViewById(R.id.txtBillingAddress1);
        final TextView txtBillingAddress2 = findViewById(R.id.txtBillingAddress2);
        final TextView txtBillingCityProvPostal = findViewById(R.id.txtBillingCityProvPostal);

        //ListViews
        final ListView lstQuoteLine = findViewById(R.id.lstQuoteLine);
        final ListView lstContact = findViewById(R.id.lstContact);

        //Buttons
        final Button btnBillingAddress = findViewById(R.id.btnBillingAddress);
        final Button btnContactList = findViewById(R.id.btnContactList);
        final Button btnQuoteLineList = findViewById(R.id.btnQuoteLineList);
        final Button btnAddContact = findViewById(R.id.btnAddContact);
        final Button btnAddLine = findViewById(R.id.btnAddLine);
        final Button btnEditHeader = findViewById(R.id.btnEditHeader);

        //Lists
        final ArrayList<QuoteLine> quoteLines;
        final ArrayList<Contact> contacts;

        //Adapters
        ContactAdapter adapterContact;
        QuoteLineAdapter adapterQuoteLine;

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
            siteAddress = database.addressDAO().getAddress(quote.siteAddressID).get(0);
            billingAddress = database.addressDAO().getAddress(quote.billingAddressID).get(0);
            company = database.companyDAO().getCompany(quote.companyID).get(0);

            //Gather lists
            contacts = new ArrayList<>(database.contactDAO().getContactsFromQuote(quote.id));
            quoteLines = new ArrayList<>(database.quoteLineDAO().getQuoteLinesForQuote(quote.id));

            //Create custom adapters
            adapterContact = new ContactAdapter(this, contacts);
            adapterQuoteLine = new QuoteLineAdapter(this, quoteLines);
        } else {
            //make toast and return to quote list
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
            startActivity(intent);
            return;
        }

        //Set text fields
        String tempString;
        txtCompanyName.setText(company.name);
        txtQuoteNumber.setText(quote.quoteNumber);
        txtSiteAddress1.setText(siteAddress.address1);
        txtSiteAddress2.setText(siteAddress.address2);
        tempString = siteAddress.city + ", " + siteAddress.province + ", " + siteAddress.postalCode;
        txtSiteCityProvPostal.setText(tempString);
        txtBillingAddress1.setText(billingAddress.address1);
        txtBillingAddress2.setText(billingAddress.address2);
        tempString = billingAddress.city + ", " + billingAddress.province + ", " + billingAddress.postalCode;
        txtBillingCityProvPostal.setText(tempString);

        //Prepare billing address button
        btnBillingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtBillingAddress1.getVisibility() == View.VISIBLE) {
                    txtBillingAddress1.setVisibility(View.GONE);
                    txtBillingAddress2.setVisibility(View.GONE);
                    txtBillingCityProvPostal.setVisibility(View.GONE);
                } else {
                    txtBillingAddress1.setVisibility(View.VISIBLE);
                    txtBillingAddress2.setVisibility(View.VISIBLE);
                    txtBillingCityProvPostal.setVisibility(View.VISIBLE);
                }
            }
        });

        //Prepare contact listView
        lstContact.setAdapter(adapterContact);
        lstContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Click listener for contacts
                final Contact a = (Contact) parent.getItemAtPosition(position);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Would you like to remove " + a.contactName + "from this quote?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add quote contact
                                database.quoteContactDAO().removeQuoteContact(quote.id, a.id);
                                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                                intent.putExtra("QUOTE_ID", quote.id);
                                startActivity(intent);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        //Prepare contact list button
        btnContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contacts.size() == 0) {
                    Toast.makeText(getApplicationContext(), "This quote has no contacts yet!", Toast.LENGTH_SHORT).show();
                } else if (lstContact.getVisibility() == View.VISIBLE) {
                    lstContact.setVisibility(View.GONE);
                } else {
                    lstContact.setVisibility(View.VISIBLE);
                    lstQuoteLine.setVisibility(View.GONE);
                }
            }
        });

        //Prepare quote line listView
        lstQuoteLine.setAdapter(adapterQuoteLine);
        lstQuoteLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuoteLine a = (QuoteLine) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_LINE_ID", a.id);
                startActivity(intent);
            }
        });

        //Prepare quote line list button
        btnQuoteLineList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quoteLines.size() == 0) {
                    Toast.makeText(getApplicationContext(), "This quote has no lines yet!", Toast.LENGTH_SHORT).show();
                } else if (lstQuoteLine.getVisibility() == View.VISIBLE) {
                    lstQuoteLine.setVisibility(View.GONE);
                } else {
                    lstQuoteLine.setVisibility(View.VISIBLE);
                    lstContact.setVisibility(View.GONE);
                }
            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuoteContactNewActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
        });

        btnAddLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuoteLineNewActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
        });

        btnEditHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuoteEditActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
        });
    }
}
