package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        TextView myAwesomeTextView = (TextView)findViewById(R.id.textView);
        int tempCompanyID;
        String tempString;


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tempString= null;
            } else {
                tempString= extras.getString("COMPANY_ID");
            }
        } else {
            tempString= (String) savedInstanceState.getSerializable("COMPANY_ID");
        }

        if (tempString != ""){
            tempCompanyID= Integer.parseInt(tempString) + 1;
            List<Company> companyOne = database.companyDao().getCompany(tempCompanyID);
            myAwesomeTextView.setText(companyOne.get(0).name);
            //myAwesomeTextView.setText(Integer.toString(companyOne.get(0).id));
        }
        else{
            myAwesomeTextView.setText("No ID found");
        }

        Button btnAddCompany = findViewById(R.id.btnAddresses);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                startActivity(intent);
            }
        });

    }
}
