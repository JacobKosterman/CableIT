package skycom.cableit.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyNewDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String tempCompanyName = "";
    String tempCompanyDescription = "";
    Context context = this;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_new_detail);

        database = AppDatabase.getDatabase(getApplicationContext());




        Button btnSaveCompany = findViewById(R.id.btnSaveCompany);
        btnSaveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText txtCompanyName = (EditText)findViewById(R.id.txtCompanyName);
                EditText txtCompanyDescription = (EditText)findViewById(R.id.txtDescription);

                tempCompanyName = txtCompanyName.getText().toString();
                tempCompanyDescription = txtCompanyDescription.getText().toString();

                List<Company> CompanyCheckList = database.companyDao().getCompanyByName(tempCompanyName);

                if (CompanyCheckList.isEmpty()){

                    addCompanyAndDivert();

                }else {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Company already exists. Create anyways?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    addCompanyAndDivert();
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            }
        });
    }

    public void addCompanyAndDivert(){

        Company company = new Company(tempCompanyName, tempCompanyDescription);
        database.companyDao().addCompany(company);
        List<Company> tempCompanyList = database.companyDao().getCompanyByName(tempCompanyName);

        if (tempCompanyList.size() == 1){

            Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
            int id = tempCompanyList.get(0).id;

            intent.putExtra("NEW_COMPANY_ID", id);
            startActivity(intent);

        } else {

            Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
            startActivity(intent);

        }

    }

}
