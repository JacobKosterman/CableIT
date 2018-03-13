package skycom.cableit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ProductDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    Boolean askSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        database = AppDatabase.getDatabase(getApplicationContext());


        EditText editProName = (EditText)findViewById(R.id.txtProName);
        EditText editProNo = (EditText)findViewById(R.id.txtProNo);
        EditText editProDescription = (EditText)findViewById(R.id.txtProDescription);
        EditText editProCost = (EditText)findViewById(R.id.txtProCost);

        String tempProName = editProName.getText().toString();
        String tempProNo = editProNo.getText().toString();
        String tempProDescription = editProDescription.getText().toString();
        String tempProCost = editProCost.getText().toString();


        if (tempProName.length() >= 1 && tempProNo.length() >= 1
                && tempProDescription.length() >= 1 && tempProCost.length() >= 1){




        }







    }
}
