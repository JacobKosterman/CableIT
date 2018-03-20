package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Product;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ProductListActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        database = AppDatabase.getDatabase(getApplicationContext());

        // Populates list view of companies.
        final List<Product> productList = database.productDao().getAllProducts();
        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";

        for (int i=0; i<productList.size(); i++){
            tempString = productList.get(i).productName;
            tempStringList.add(tempString);
        }

        ListView listView = (ListView) findViewById(R.id.lstProduct);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        tempStringList
                );
        listView.setAdapter(arrayAdapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                startActivity(intent);

            }
        });


    }
}
