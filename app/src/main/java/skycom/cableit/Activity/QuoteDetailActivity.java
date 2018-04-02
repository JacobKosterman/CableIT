package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteDetailActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        // Populates list view of companies.
        final List<QuoteLine> quoteList = database.quoteLineDAO().getAllQuoteLines();
        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";

        for (int i = 0; i < quoteList.size(); i++) {
            tempString = String.valueOf(quoteList.get(i).productID);
            tempStringList.add(tempString);
        }

        ListView listView = (ListView) findViewById(R.id.lstQuoteLine);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        tempStringList
                );
        listView.setAdapter(arrayAdapter);

        Button btnAddProduct = findViewById(R.id.btnAddQuoteLine);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
