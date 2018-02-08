package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import skycom.cableit.R;

public class AddressDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);




        Button btnSaveCompany = findViewById(R.id.btnSaveCompany);
        btnSaveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create editText Objects
                EditText editAddress1 = findViewById(R.id.txtStreetAddress1);
                EditText editAddress2 = findViewById(R.id.txtStreetAddress2);
                EditText editUnitNumber = findViewById(R.id.txtUnitNumber);
                EditText editPostalCode = findViewById(R.id.txtPostalCode);
                EditText editProvince = findViewById(R.id.txtProvince);
                EditText editCountry = findViewById(R.id.txtCountry);

                String tempAddress1 = editAddress1.getText().toString();
                String tempAddress2 = editAddress2.getText().toString();
                String tempUnitNumber = editUnitNumber.getText().toString();
                String tempPostalCode = editPostalCode.getText().toString();
                String tempProvince= editProvince.getText().toString();
                String tempCountry = editCountry.getText().toString();

                if(!editAddress1.getText().toString().equals("") && !editUnitNumber.getText().toString().equals("") && !editPostalCode.getText().toString().equals("") &&
                        !editProvince.getText().toString().equals("") && !editCountry.getText().toString().equals("")){





                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddressDetailActivity.this);
                    builder.setMessage("");
                    builder.setTitle("Login Error");
                    //AlertDialog dialog = builder.create();
                    builder.create();
                    builder.show();

                }


                Intent intent = new Intent(getApplicationContext(), AddressListActivity.class);
                startActivity(intent);

            }
        });

    }


}
