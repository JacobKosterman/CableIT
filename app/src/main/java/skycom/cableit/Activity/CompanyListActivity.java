package skycom.cableit.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Adapters.CompanyAdapter;
import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Company;

import skycom.cableit.Classes.Contact;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;
import android.content.Intent;
import android.widget.AdapterView;

import android.view.View;
import android.widget.Toast;

public class CompanyListActivity extends AppCompatActivity {

    private AppDatabase database;

    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;
    String companyIDString = "";

    ListView lvCompany;
    private static CompanyAdapter adapterCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        database = AppDatabase.getDatabase(getApplicationContext());

        // Populates list view of companies.
        final List<Company> companyList = database.companyDAO().getAllCompany();
        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";
//



//        if (companyList.size() >= 1 ){
//            for (int i=0; i<companyList.size(); i++){
//                tempString = companyList.get(i).name;
//                tempStringList.add(tempString);
//            }
//            ListView listView = (ListView) findViewById(R.id.lstView);
//            ArrayAdapter<String> arrayAdapter =
//                    new ArrayAdapter<String>(this,
//                            android.R.layout.simple_list_item_1,
//                            tempStringList
//                    );
//
//
//            listView.setAdapter(arrayAdapter);
//            listView.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
//                    intent.putExtra("COMPANY_ID", id + "");
//                    startActivity(intent);
//                }
//            });
//
//        }


        lvCompany =(ListView)findViewById(R.id.lstCompany);
        ArrayList<Company> temp = new ArrayList<>();
        temp.addAll(database.companyDAO().getAllCompany());

        adapterCompany = new CompanyAdapter(this,temp);

        lvCompany.setAdapter(adapterCompany);
        lvCompany.setVisibility(View.VISIBLE);
        lvCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company c = (Company) parent.getItemAtPosition(position);
                if (c != null)
                {

                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);

                        //intent.putExtra("COMPANY_ID_TEST", companyID + "");

                        existingCompanyPrefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
                        SharedPreferences.Editor editor = existingCompanyPrefs.edit();
                        editor.putInt("MY_COMPANY", c.id);
                        editor.apply();
                        startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });






        //Needs to be completed to add company
        Button btnProductList = findViewById(R.id.btnAddCompany);
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyNewDetailActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
