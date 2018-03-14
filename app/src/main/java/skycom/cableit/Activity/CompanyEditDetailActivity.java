package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyEditDetailActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit_detail);

        database = AppDatabase.getDatabase(getApplicationContext());




    }
}
