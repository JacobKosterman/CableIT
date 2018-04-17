package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    Address address;

    String addOne = "";
    String addTwo = "";
    String city = "";
    String postalCode = "";
    String province = "";
    String country = "";
    String addressType = "";
    Boolean checked;

    AddressType addressTypeObj;

    Button btnMakeActive;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);
        addressID = prefs.getInt("MY_ADDRESS", 0);

        List<Address> tempAddress = database.addressDAO().getAddress(addressID);

        TextView editAddressOne = (TextView) findViewById(R.id.txtStreetAddress1);
        TextView editAddressType = (TextView)findViewById(R.id.txtAddressType);
        final CheckBox chkIsActive = (CheckBox)findViewById(R.id.chkIsActive);
        btnMakeActive = (Button) findViewById(R.id.btnMakeActive);

        if (!tempAddress.isEmpty()){
            address = tempAddress.get(0);

            btnMakeActive.setText(address.isActive == Boolean.TRUE ? "Deactivate" : "Activate");

            addOne = address.address1;
            addTwo = address.address2;
            city = address.city;
            postalCode = address.postalCode;
            province = address.province;
            country = address.country;
            checked = address.isActive;



            String appendedAddress = "";
            if(addTwo.isEmpty() && addTwo != null){
                appendedAddress = (addOne != "" ? addOne + " \n" : "") +
                        (city != "" ? city + ", " : "") +
                        (province != "" ? province + ", " : "") +
                        (postalCode != "" ? postalCode + " \n" : "") +
                        (country != "" ? country + " " : "");
            }else{
                appendedAddress = (addOne != "" ? addOne + " \n" : "") +
                        (addTwo != "" ? addTwo + " \n" : null) +
                        (city != "" ? city + ", " : "") +
                        (province != "" ? province + ", " : "") +
                        (postalCode != "" ? postalCode + " \n" : "") +
                        (country != "" ? country + " " : "");
            }

            int tempAddTypeID = tempAddress.get(0).addressTypeID;
            addressTypeObj = AddressType.valueOf(tempAddTypeID);

            addressType = addressTypeObj.name();


            editAddressOne.setText(appendedAddress, TextView.BufferType.EDITABLE);
            editAddressType.setText("Type: " + addressType, TextView.BufferType.EDITABLE);
            chkIsActive.setChecked(checked);
        }

        Button btnEditContact = findViewById(R.id.btnEdit);
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ContactEditDetailActivity.class);
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

        btnMakeActive = findViewById(R.id.btnMakeActive);
        btnMakeActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chkIsActive.isChecked()){
                    chkIsActive.setChecked(Boolean.FALSE);
                    address.isActive = Boolean.FALSE;
                    btnMakeActive.setText("Activate");
                }else{
                    chkIsActive.setChecked(Boolean.TRUE);
                    address.isActive = Boolean.TRUE;
                    btnMakeActive.setText("Deactivate");
                }

                database.addressDAO().updateAddress(address);
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
