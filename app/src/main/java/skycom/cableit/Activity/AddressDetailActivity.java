package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.AddressType;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class AddressDetailActivity extends AppCompatActivity {

    int companyID = 0;
    int addressID = 0;

    private AppDatabase database;

    String addOne = "";
    String addTwo = "";
    String city = "";
    String postalCode = "";
    String province = "";
    String country = "";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);
        addressID = prefs.getInt("MY_ADDRESS", 0);

        List<Address> tempAddress = database.addressDAO().getAddress(addressID);

        EditText editAddressOne = (EditText)findViewById(R.id.txtStreetAddress1);
        EditText editAddressTwo = (EditText)findViewById(R.id.txtStreetAddress2);
        EditText editCity = (EditText)findViewById(R.id.txtCity);
        EditText editPostal = (EditText)findViewById(R.id.txtPostalCode);
        EditText editProvince = (EditText)findViewById(R.id.txtProvince);
        EditText editCountry = (EditText)findViewById(R.id.txtCountry);


        if (!tempAddress.isEmpty()){

            addOne = tempAddress.get(0).address1;
            addTwo = tempAddress.get(0).address2;
            city = tempAddress.get(0).city;
            postalCode = tempAddress.get(0).postalCode;
            province = tempAddress.get(0).province;
            country = tempAddress.get(0).country;

            editAddressOne.setText(addOne, TextView.BufferType.EDITABLE);
            editAddressTwo.setText(addTwo, TextView.BufferType.EDITABLE);
            editCity.setText(city, TextView.BufferType.EDITABLE);
            editPostal.setText(postalCode, TextView.BufferType.EDITABLE);
            editProvince.setText(province, TextView.BufferType.EDITABLE);
            editCountry.setText(country, TextView.BufferType.EDITABLE);

        }


        Button btnEditContact = findViewById(R.id.btnEdit);
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ContactEditDetailActivity.class);
                intent.putExtra("NEW_COMPANY_ID", companyID);
                startActivity(intent);
            }
        });

        Button btnAddAddress = findViewById(R.id.btnEdit);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddressEditDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openMaps(View view){
        String appendedAddress = (addOne != "" ? addOne + " " : "") +
                (addTwo != "" ? addTwo + " " : "") +
                (city != "" ? city + " " : "") +
                (postalCode != "" ? postalCode + " " : "") +
                (province != "" ? province + " " : "") +
                (country != "" ? country + " " : "");

        Uri tempUri = Uri.parse("geo:0,0?q=" + appendedAddress);
        Intent intent = new Intent(Intent.ACTION_VIEW, tempUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    };

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra("NEW_COMPANY_ID", companyID);
        startActivity(intent);
    }
}
