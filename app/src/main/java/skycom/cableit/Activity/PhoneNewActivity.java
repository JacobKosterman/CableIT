package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import skycom.cableit.Classes.Phone;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class PhoneNewActivity extends AppCompatActivity {

    private AppDatabase database;
    int phoneID;

    TextView tvNumber;
    TextView tvExt;
    TextView tvDesc;

    Phone phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_new);






    }
}
