package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class NewQuoteActivity extends AppCompatActivity {

    private AppDatabase database;
    public String quoteNumber;
    public int companyID;
    public int siteAddressID;
    public int billingAddressID;
    public Date dateCreated;
    public Date dateUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);
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
        final EditText txtCompanyID = findViewById(R.id.txtCompanyID);
        final EditText txtSiteAddressID = findViewById(R.id.txtSiteAddressID);
        final EditText txtBillingAddressID = findViewById(R.id.txtBillingAddressID);

        txtQuoteNumber.setText(quoteNumber);
        txtQuoteNumber.setInputType(InputType.TYPE_NULL);

        Button btnSaveQuote = findViewById(R.id.btnSaveQuote);
        btnSaveQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on save here
                companyID = Integer.parseInt(txtCompanyID.getText().toString());
                siteAddressID = Integer.parseInt(txtSiteAddressID.getText().toString());
                billingAddressID = Integer.parseInt(txtBillingAddressID.getText().toString());
                dateCreated = new Date();
                dateUpdated = new Date();

                List<Quote> tempQuotes = database.quoteDAO().getQuotesFromNumber(quoteNumber);
                if (tempQuotes.size() == 0) {
                    database.quoteDAO().addQuote(new Quote(quoteNumber, companyID, siteAddressID,
                            billingAddressID, dateCreated, dateUpdated));

                    tempQuotes = database.quoteDAO().getQuotesFromNumber(quoteNumber);

                    Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                    intent.putExtra("QUOTE_ID", tempQuotes.get(0).id);
                    startActivity(intent);
                }
            }
        });
    }
}
