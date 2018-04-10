package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.AddressType;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class AddressDetailActivity extends AppCompatActivity {

    int companyID = 0;

    private AppDatabase database;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        List<Address> tempAddress = database.addressDAO().getAddressFromCompany(companyID);

        EditText editAddressOne = (EditText)findViewById(R.id.txtStreetAddress1);
        EditText editAddressTwo = (EditText)findViewById(R.id.txtStreetAddress2);
        EditText editCity = (EditText)findViewById(R.id.txtCity);
        EditText editPostal = (EditText)findViewById(R.id.txtPostalCode);
        EditText editProvince = (EditText)findViewById(R.id.txtProvince);
        EditText editCountry = (EditText)findViewById(R.id.txtCountry);


        if (!tempAddress.isEmpty()){
            editAddressOne.setText(String.valueOf(tempAddress.get(0).address1), TextView.BufferType.EDITABLE);
            editAddressTwo.setText(String.valueOf(tempAddress.get(0).address2), TextView.BufferType.EDITABLE);
            editCity.setText(String.valueOf(tempAddress.get(0).city), TextView.BufferType.EDITABLE);
            editPostal.setText(String.valueOf(tempAddress.get(0).postalCode), TextView.BufferType.EDITABLE);
            editProvince.setText(String.valueOf(tempAddress.get(0).province), TextView.BufferType.EDITABLE);
            editCountry.setText(String.valueOf(tempAddress.get(0).country), TextView.BufferType.EDITABLE);
        }

//        Spinner mySpinner = (Spinner) findViewById(R.id.spinAddressType);
//
//        mySpinner.setAdapter(new ArrayAdapter<AddressType>(this,
//                android.R.layout.simple_spinner_item, AddressType.values()));

        Button btnAddAddress = findViewById(R.id.btnEdit);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddressEditDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra("NEW_COMPANY_ID", companyID);
        startActivity(intent);
    }

}
