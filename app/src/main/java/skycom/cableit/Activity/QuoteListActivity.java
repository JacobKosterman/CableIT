package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteListActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_list);

        database = AppDatabase.getDatabase(getApplicationContext());

        // Populates list view of companies.
        final List<Quote> quoteList = database.quoteDAO().getAllQuotes();
        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";

        for (int i = 0; i < quoteList.size(); i++) {
            tempString = quoteList.get(i).quoteNumber;
            tempStringList.add(tempString);
        }

        ListView listView = (ListView) findViewById(R.id.lstQuote);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        tempStringList
                );
        listView.setAdapter(arrayAdapter);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), QuoteDetailActivity.class);
                intent.putExtra("QUOTE_ID", id + "");
                startActivity(intent);
            }
        });

        Button btnAddProduct = findViewById(R.id.btnAddQuote);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
