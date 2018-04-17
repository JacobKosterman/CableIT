package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import skycom.cableit.Classes.Phone;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class PhoneDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int phoneID;

    TextView tvNumber;
    TextView tvExt;
    TextView tvDesc;

    Phone phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        phoneID = prefs.getInt("MY_PHONE", 0);


        tvNumber = (TextView) findViewById(R.id.txtNumber);
        tvExt = (TextView) findViewById(R.id.txtExt);
        tvDesc = (TextView)findViewById(R.id.txtDescription);

        phone = database.phoneDAO().getPhone(phoneID);

        if (phone != null){
            tvNumber.setText(phone.phoneNumber);
            tvExt.setText(phone.ext);
            tvDesc.setText(phone.description);
        }


        Button btnEditPhone = findViewById(R.id.btnEdit);
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), PhoneEditDetailActivity.class);
                //startActivity(intent);
            }
        });
    }

}
