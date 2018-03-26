package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String companyIDString = "";
    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;
    int newCompanyID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        TextView myAwesomeTextView = (TextView)findViewById(R.id.textView);
        int tempCompanyID;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                companyIDString = null;
            } else {
                companyIDString = extras.getString("COMPANY_ID");
            }
        } else {
            companyIDString = (String) savedInstanceState.getSerializable("COMPANY_ID");
        }

        if (companyIDString != null)
        {
            if (companyIDString != ""){
                tempCompanyID= Integer.parseInt(companyIDString) + 1;
                companyID = tempCompanyID;
                List<Company> companyOne = database.companyDao().getCompany(tempCompanyID);
                myAwesomeTextView.setText(companyOne.get(0).name);
            }
            else{
                myAwesomeTextView.setText("No ID found");
            }
        }

        //This is used if creating a new company.
        if (companyIDString == "" || companyIDString == null){

            Bundle bundle = getIntent().getExtras();
            if (bundle != null )
                newCompanyID = bundle.getInt("NEW_COMPANY_ID");

            List<Company> companyOne = database.companyDao().getCompany(newCompanyID);
            myAwesomeTextView.setText(companyOne.get(0).name);
        }


        existingCompanyPrefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        SharedPreferences.Editor editor = existingCompanyPrefs.edit();
        editor.putInt("MY_COMPANY", companyID);
        editor.apply();


        //Button to navigate to the Address detail Activity
        Button btnAddCompany = findViewById(R.id.btnAddresses);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                if (companyIDString != ""){
                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
    }
}
