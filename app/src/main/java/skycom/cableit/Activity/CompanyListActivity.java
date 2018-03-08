package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class CompanyListActivity extends AppCompatActivity {
//AppCompatActivity

    private AppDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        database = AppDatabase.getDatabase(getApplicationContext());

        final List<Company> companyList = database.companyDao().getAllCompany();

        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";


        for (int i=0; i<companyList.size(); i++){
            tempString = companyList.get(i).name;
            tempStringList.add(tempString);
        }

        ListView listView = (ListView) findViewById(R.id.lstView);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                tempStringList
        );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            // Hey Mirko, Can you take a look at this? It can't find the intent when a company is
            // clicked on in the listview.
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                //String message = "abc";
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });



    }


}
