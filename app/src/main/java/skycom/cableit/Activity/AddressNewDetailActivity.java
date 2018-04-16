package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.AddressType;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class AddressNewDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;

    EditText editAddressOne;
    EditText editAddressTwo;
    EditText editCity;
    EditText editPostal;
    EditText editProvince;
    EditText editCountry;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_new_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        editAddressOne = (EditText)findViewById(R.id.txtNewStreetAddress1);
        editAddressTwo = (EditText)findViewById(R.id.txtNewStreetAddress2);
        editCity = (EditText)findViewById(R.id.txtNewCity);
        editPostal = (EditText)findViewById(R.id.txtNewPostalCode);
        editProvince = (EditText)findViewById(R.id.txtNewProvince);
        editCountry = (EditText)findViewById(R.id.txtNewCountry);


        //This creates and populates the AddressType Spinner
        mySpinner = (Spinner) findViewById(R.id.spnAddressType);
        mySpinner.setAdapter(new ArrayAdapter<AddressType>(this, android.R.layout.simple_spinner_item, AddressType.values()));


        Button btnNewAddressList = findViewById(R.id.btnSaveNewAddress);
        btnNewAddressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tempAddOne = editAddressOne.getText().toString();
                String tempAddTwo = editAddressTwo.getText().toString();
                String tempCity = editCity.getText().toString();
                String tempPostal = editPostal.getText().toString();
                String tempProvince = editProvince.getText().toString();
                String tempCountry = editCountry.getText().toString();

                AddressType tempValue = (AddressType) mySpinner.getSelectedItem();
                int tempAddressType = tempValue.getValue();

                if (tempAddOne.length() >= 1 && !tempAddOne.isEmpty()){

                    database.addressDAO().addAddress(new Address(companyID, tempAddressType, tempAddOne, tempAddTwo,
                            tempCity, tempPostal, tempProvince, tempCountry, Boolean.TRUE));


                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    startActivity(intent);

                }
            }
        });


    }


}
