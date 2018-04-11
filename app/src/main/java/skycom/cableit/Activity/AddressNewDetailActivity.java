package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import skycom.cableit.Classes.Address;
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


                if (tempAddOne.length() >= 1 && !tempAddOne.isEmpty()){

                    database.addressDAO().addAddress(new Address(companyID,1, tempAddOne, tempAddTwo,
                            tempCity, tempPostal, tempProvince, tempCountry, Boolean.TRUE));


                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    intent.putExtra("COMPANY_ID", companyID - 1 + "");
                    startActivity(intent);

                }
            }
        });
    }
}
