package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactDetailActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        database = AppDatabase.getDatabase(getApplicationContext());





    }
}
