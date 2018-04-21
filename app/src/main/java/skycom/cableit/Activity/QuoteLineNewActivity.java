package skycom.cableit.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteLineNewActivity extends AppCompatActivity {

    Quote quote = null;
    Context context = this;
    AppDatabase database;
    Product product;
    TextView txtLineComment;
    TextView txtQuantity;
    TextView txtProductCost;
    TextView txtMarkupRate;
    TextView txtMarkupAmount;
    TextView txtLineTotal;
    TextView txtItemPrice;
    Spinner spnProductID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_line_new);
        database = AppDatabase.getDatabase(getApplicationContext());
        txtLineComment = findViewById(R.id.txtLineComment);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtProductCost = findViewById(R.id.txtProductCost);
        txtMarkupRate = findViewById(R.id.txtMarkupRate);
        txtMarkupAmount = findViewById(R.id.txtMarkupAmount);
        txtItemPrice = findViewById(R.id.txtItemPrice);
        txtLineTotal = findViewById(R.id.txtLineTotal);
        spnProductID = findViewById(R.id.spnProductID);

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

        List<Product> products = database.productDAO().getAllProducts();


        ArrayAdapter productAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, products);

        spnProductID.setAdapter(productAdapter);

        spnProductID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                product = (Product) spnProductID.getItemAtPosition(position);

                txtMarkupAmount.setText(String.valueOf(product.markupAmount));
                txtMarkupRate.setText(String.valueOf(product.markupRate));
                txtProductCost.setText(String.valueOf(product.productCost));

                updateCalulatedFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        txtMarkupRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //SAVE THE DATA
                    updateCalulatedFields();
                }

            }
        });

        txtProductCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //SAVE THE DATA
                    updateCalulatedFields();
                }

            }
        });

        txtMarkupAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //SAVE THE DATA
                    updateCalulatedFields();
                }

            }
        });

        txtQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //SAVE THE DATA
                    updateCalulatedFields();
                }

            }
        });

        Button btnSaveLine = findViewById(R.id.btnSaveLine);
        btnSaveLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lineComment = txtLineComment.getText().toString();
                Double qty = Double.parseDouble(txtQuantity.getText().toString());
                Double productCost = Double.parseDouble(txtProductCost.getText().toString());
                Double markupRate = Double.parseDouble(txtMarkupRate.getText().toString());
                Double markupAmount = Double.parseDouble(txtMarkupAmount.getText().toString());

                database.quoteLineDAO().addQuoteLine(new QuoteLine(quote.id, product.id, lineComment,
                        qty, productCost, markupRate, markupAmount));

                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_ID", quote.id);
                startActivity(intent);
            }
        });
    }

    public void updateCalulatedFields() {
        Double mkAmt;
        Double mkRt;
        Double cost;
        Double qty;

        try {
            mkAmt = Double.parseDouble(txtMarkupAmount.getText().toString());
        } catch (Exception e) {
            mkAmt = 0.0;
        }

        try {
            mkRt = Double.parseDouble(txtMarkupRate.getText().toString());
        } catch (Exception e) {
            mkRt = 0.0;
        }

        try {
            cost = Double.parseDouble(txtProductCost.getText().toString());
        } catch (Exception e) {
            cost = 0.0;
        }

        try {
            qty = Double.parseDouble(txtQuantity.getText().toString());
        } catch (Exception e) {
            qty = 0.0;
        }
        Double price = (cost * ((mkRt / 100) + 1)) + mkAmt;

        txtItemPrice.setText(String.valueOf(price));
        txtLineTotal.setText(String.valueOf(price * qty));
    }
}
