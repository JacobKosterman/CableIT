package skycom.cableit.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteLineNewActivity extends AppCompatActivity {

    Quote quote = null;
    Context context = this;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_line_new);
        database = AppDatabase.getDatabase(getApplicationContext());

        final TextView txtProductID = findViewById(R.id.txtProductID);
        final TextView txtLineComment = findViewById(R.id.txtLineComment);
        final TextView txtQuantity = findViewById(R.id.txtQuantity);
        final TextView txtProductCost = findViewById(R.id.txtProductCost);
        final TextView txtMarkupRate = findViewById(R.id.txtMarkupRate);
        final TextView txtMarkupAmount = findViewById(R.id.txtMarkupAmount);

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
        } else {
            //make toast and return to quote list
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
            startActivity(intent);
            return;
        }

        Button btnSaveLine = findViewById(R.id.btnSaveLine);
        btnSaveLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int productID = Integer.parseInt(txtProductID.getText().toString());
                String lineComment = txtLineComment.getText().toString();
                Double qty = Double.parseDouble(txtQuantity.getText().toString());
                Double productCost = Double.parseDouble(txtProductCost.getText().toString());
                Double markupRate = Double.parseDouble(txtMarkupRate.getText().toString());
                Double markupAmount = Double.parseDouble(txtMarkupAmount.getText().toString());

                database.quoteLineDAO().addQuoteLine(new QuoteLine(quote.id,productID,lineComment,qty,productCost,markupRate,markupAmount));

                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
        });
    }
}
