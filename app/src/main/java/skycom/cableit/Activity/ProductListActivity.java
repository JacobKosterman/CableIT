package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
        final List<Product> productList = database.productDAO().getAllProducts();
        List<String> tempStringList = new ArrayList<String>();
        String tempString = "";

        if (productList.size() >= 1){
            for (int i=0; i<productList.size(); i++){
                tempString = "Part Number: " + productList.get(i).productName + "\n Part Name: " + productList.get(i).partNo;
                tempStringList.add(tempString);
            }

            ListView listView = (ListView) findViewById(R.id.lstProduct);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            tempStringList
                    );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                    intent.putExtra("PRODUCT_ID", id + "");
                    startActivity(intent);
                }
            });
        }


        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProductNewDetailActivity.class);
                startActivity(intent);

            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
