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

import skycom.cableit.Classes.Adapters.QuoteAdapter;
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

        ListView listView = (ListView) findViewById(R.id.lstQuote);
        ArrayList<Quote> temp = new ArrayList<>();
        temp.addAll(database.quoteDAO().getAllQuotes());

        QuoteAdapter adapterQuote = new QuoteAdapter(this,temp);

        listView.setAdapter(adapterQuote);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //click handler
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
