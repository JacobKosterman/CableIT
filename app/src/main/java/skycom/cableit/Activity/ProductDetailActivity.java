package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import skycom.cableit.Classes.AddressType;
import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.ProductCategory;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ProductDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    Boolean askSave;
    Product product;

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





        Button btnProductList = findViewById(R.id.btnSaveProduct);
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);
            }
        });

        /*if (tempProName.length() >= 1 && tempProNo.length() >= 1
                && tempProDescription.length() >= 1 && tempProCost.length() >= 1){

                product.productName =tempProName;
                product.partNo = tempProNo;
                product.description = tempProDescription;
                product.productCost = Double.parseDouble(tempProCost);


                database.productDao().addProduct(product);


        }*/







    }
}
