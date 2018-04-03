package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;


import java.util.List;

import skycom.cableit.Classes.TabFragment;
import skycom.cableit.Classes.ViewPagerAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String companyIDString = "";
    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;
    int newCompanyID = 0;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TabFragment TabFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        TextView txtName = (TextView)findViewById(R.id.textName);
        TextView txtDescription = (TextView)findViewById(R.id.textDescription);
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
                List<Company> companyOne = database.companyDAO().getCompany(tempCompanyID);
                txtName.setText(companyOne.get(0).name);
                txtDescription.setText(companyOne.get(0).description);
            }
            else{
                txtName.setText("No ID found");
            }
        }

        //This is used if creating a new company.
        if (companyIDString == "" || companyIDString == null){

            Bundle bundle = getIntent().getExtras();
            if (bundle != null )
                newCompanyID = bundle.getInt("NEW_COMPANY_ID");

            List<Company> companyOne = database.companyDAO().getCompany(newCompanyID);
            txtName.setText(companyOne.get(0).name);
            txtDescription.setText(companyOne.get(0).description);
        }


        //Adding the current company ID to sharedPrefs
        existingCompanyPrefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        SharedPreferences.Editor editor = existingCompanyPrefs.edit();
        editor.putInt("MY_COMPANY", companyID);
        editor.apply();


        //To be deleted - Just leave for testing
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);







        //Button to navigate to the Address detail Activity
        Button btnEditCompany = findViewById(R.id.btnEditCompany);
        btnEditCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyEditDetailActivity.class);
                if (companyIDString != ""){
                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
                    startActivity(intent);
                }
            }
        });

//        Button btnAddAddress = findViewById(R.id.btnNewAddress);
//        btnAddAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddressEditDetailActivity.class);
//                if (companyIDString != ""){
//                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
//                    startActivity(intent);
//                }
//            }
//        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
    }

//    public void myClickMethod(View v) {
//        TabFragment.onClick(v);
//    }
}
