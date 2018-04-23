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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteLineEditActivity extends AppCompatActivity {

    QuoteLine quoteLine = null;
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
    TextView txtProductID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_line_edit);
        database = AppDatabase.getDatabase(getApplicationContext());
        txtLineComment = findViewById(R.id.txtLineComment);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtProductCost = findViewById(R.id.txtProductCost);
        txtMarkupRate = findViewById(R.id.txtMarkupRate);
        txtMarkupAmount = findViewById(R.id.txtMarkupAmount);
        txtItemPrice = findViewById(R.id.txtItemPrice);
        txtLineTotal = findViewById(R.id.txtLineTotal);
        txtProductID = findViewById(R.id.txtProductID);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                quoteLine = database.quoteLineDAO().getQuoteLine(extras.getInt("QUOTE_LINE_ID")).get(0);
            }
        } else {
            quoteLine = database.quoteLineDAO().getQuoteLine((int) savedInstanceState.getSerializable("QUOTE_LINE_ID")).get(0);
        }

        if (quoteLine == null) {
            //make toast and return to quote list
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuoteListActivity.class);
            startActivity(intent);
            return;
        }

        product = database.productDAO().getProduct(quoteLine.productID).get(0);

        txtLineComment.setText(quoteLine.lineComment);
        txtQuantity.setText(quoteLine.quantity.toString());
        txtMarkupAmount.setText(quoteLine.markupAmount.toString());
        txtMarkupRate.setText(quoteLine.markupRate.toString());
        txtProductCost.setText(quoteLine.productCost.toString());
        txtProductID.setText(product.productName);

        txtProductID.setInputType(InputType.TYPE_NULL);
        updateCalulatedFields();


        txtMarkupRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateCalulatedFields();
                }
            }
        });

        txtProductCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateCalulatedFields();
                }
            }
        });

        txtMarkupAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateCalulatedFields();
                }
            }
        });

        txtQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateCalulatedFields();
                }

            }
        });

        Button btnSaveLine = findViewById(R.id.btnSaveLine);
        btnSaveLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lineComment = txtLineComment.getText().toString();

                quoteLine.markupAmount = Double.parseDouble(txtMarkupAmount.getText().toString());
                quoteLine.markupRate = Double.parseDouble(txtMarkupRate.getText().toString());
                quoteLine.lineComment = txtLineComment.getText().toString();
                quoteLine.productCost = Double.parseDouble(txtProductCost.getText().toString());
                quoteLine.quantity = Double.parseDouble(txtQuantity.getText().toString());

                database.quoteLineDAO().updateQuoteLine(quoteLine);

                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_ID", quoteLine.quoteID);
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
