package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactEditDetailActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

    }
}
