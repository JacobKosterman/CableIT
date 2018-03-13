package skycom.cableit.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Company;

import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import android.view.View;

public class CompanyListActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        database = AppDatabase.getDatabase(getApplicationContext());


        // Populates list view of companies.
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

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                intent.putExtra("COMPANY_ID", id + "");
                startActivity(intent);
            }
        });








    }


}
