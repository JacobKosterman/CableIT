package skycom.cableit.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteContact;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteContactNewActivity extends AppCompatActivity {
    Context context = this;
    Quote quote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_contact_new);

        final AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        Company company;
        final ListView lstContact = findViewById(R.id.lstContact);
        final ArrayList<Contact> contacts;
        ContactAdapter adapterContact;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                quote = database.quoteDAO().getQuote(extras.getInt("QUOTE_ID")).get(0);
            }
        } else {
            quote = database.quoteDAO().getQuote((int)savedInstanceState.getSerializable("QUOTE_ID")).get(0);
        }

        if (quote != null) {
            //Gather list
            contacts = new ArrayList<>(database.contactDAO().getContactsFromCompanyNotOnQuote(quote.companyID,quote.id));
            if(contacts.size() == 0)
            {
                Toast.makeText(getApplicationContext(), "No contacts available!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
            //Create custom adapters
            adapterContact = new ContactAdapter(this, contacts);
        } else {
            //make toast and return to quote list
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
            startActivity(intent);
            return;
        }

        //Prepare contact listView
        lstContact.setAdapter(adapterContact);
        lstContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Click listener for contacts
                final Contact a = (Contact) parent.getItemAtPosition(position);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Would you like to add " + a.contactName + " to this quote?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add quote contact
                                QuoteContact newQuoteContact = new QuoteContact(a.id,quote.id);
                                database.quoteContactDAO().addQuoteContact(newQuoteContact);
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
    }
}
