package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyEditDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;
    Company company;

    String tempName = "";
    String tempDesc = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit_detail);


        final EditText editName  = (EditText)findViewById(R.id.txtCompanyEditName);
        final EditText editDescription = (EditText)findViewById(R.id.txtCompanyEditDescription);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        List<Company> tempCompList = database.companyDao().getCompany(companyID);

        if(tempCompList.size() == 1){


            company = tempCompList.get(0);

            editName.setText(company.name);
            editDescription.setText(company.description);


        }

        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempName = editName.getText().toString();
                tempDesc = editDescription.getText().toString();

                company.name = tempName;
                company.description = tempDesc;

                database.companyDao().updateCompany(company);


                Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                intent.putExtra("COMPANY_ID", companyID - 1 + "");
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra("COMPANY_ID", companyID - 1 + "");
        startActivity(intent);
    }

}
