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

public class AddressEditDetailActivity extends AppCompatActivity {

    int companyID = 0;
    int addressID = 0;
    private AppDatabase database;
    Address address;

    String tempAddOne = "";
    String tempAddTwo = "";
    String tempCity = "";
    String tempPostal = "";
    String tempProvince = "";
    String tempCountry = "";

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
        setContentView(R.layout.activity_address_edit_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);
        addressID = prefs.getInt("MY_ADDRESS", 0);

        final List<Address> tempAddress = database.addressDAO().getAddress(addressID);


        editAddressOne = (EditText)findViewById(R.id.txtStreetAddress1);
        editAddressTwo = (EditText)findViewById(R.id.txtStreetAddress2);
        editCity = (EditText)findViewById(R.id.txtCity);
        editPostal = (EditText)findViewById(R.id.txtPostalCode);
        editProvince = (EditText)findViewById(R.id.txtProvince);
        editCountry = (EditText)findViewById(R.id.txtCountry);
        mySpinner = (Spinner) findViewById(R.id.spnAddressType);

        if (!tempAddress.isEmpty()){

            address = tempAddress.get(0);
            editAddressOne.setText(address.address1, TextView.BufferType.EDITABLE);
            editAddressTwo.setText(address.address2, TextView.BufferType.EDITABLE);
            editCity.setText(address.city, TextView.BufferType.EDITABLE);
            editPostal.setText(address.postalCode, TextView.BufferType.EDITABLE);
            editProvince.setText(address.province, TextView.BufferType.EDITABLE);
            editCountry.setText(address.country, TextView.BufferType.EDITABLE);
            mySpinner.setAdapter(new ArrayAdapter<AddressType>(this, android.R.layout.simple_spinner_item, AddressType.values()));

        }

        Button btnSaveAddAddress = findViewById(R.id.btnSaveAddress);
        btnSaveAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempAddOne = editAddressOne.getText().toString();
                tempAddTwo = editAddressTwo.getText().toString();
                tempCity = editCity.getText().toString();
                tempPostal = editPostal.getText().toString();
                tempProvince = editProvince.getText().toString();
                tempCountry = editCountry.getText().toString();


                AddressType tempValue = (AddressType) mySpinner.getSelectedItem();
                int tempAddressType = tempValue.getValue();


                address.address1 =tempAddOne;
                address.address2 = tempAddTwo;
                address.city = tempCity;
                address.postalCode = tempPostal;
                address.province = tempProvince;
                address.country = tempCountry;
                address.addressTypeID = tempAddressType;

                database.addressDAO().updateAddress(address);

                Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AddressDetailActivity.class);
        //intent.putExtra("COMPANY_ID_TEST", companyID + "");
        startActivity(intent);
    }
}
