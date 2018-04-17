package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import skycom.cableit.Classes.Adapters.CompanyAdapter;
import skycom.cableit.Classes.Adapters.ProductAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Product;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ProductListActivity extends AppCompatActivity {

    private AppDatabase database;

    SharedPreferences prefs;

    ListView lvProduct;
    private static ProductAdapter adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        database = AppDatabase.getDatabase(getApplicationContext());

        lvProduct =(ListView)findViewById(R.id.lstProduct);
        ArrayList<Product> temp = new ArrayList<>();
        temp.addAll(database.productDAO().getAllProducts());

        adapterProduct = new ProductAdapter(this,temp);

        lvProduct.setAdapter(adapterProduct);
        lvProduct.setVisibility(View.VISIBLE);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product p = (Product) parent.getItemAtPosition(position);
                if (p != null)
                {

                    Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);

                    prefs = getSharedPreferences("PRODUCT_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("MY_PRODUCT", p.id);
                    editor.apply();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });







        // Populates list view.
//        final List<Product> productList = database.productDAO().getAllProducts();
//        List<String> tempStringList = new ArrayList<String>();
//        String tempString = "";
//
//        if (productList.size() >= 1){
//            for (int i=0; i<productList.size(); i++){
//                tempString = "Part Number: " + productList.get(i).productName + "\n Part Name: " + productList.get(i).partNo;
//                tempStringList.add(tempString);
//            }
//
//            ListView listView = (ListView) findViewById(R.id.lstProduct);
//            ArrayAdapter<String> arrayAdapter =
//                    new ArrayAdapter<String>(this,
//                            android.R.layout.simple_list_item_1,
//                            tempStringList
//                    );
//            listView.setAdapter(arrayAdapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
//                    intent.putExtra("PRODUCT_ID", id + "");
//                    startActivity(intent);
//                }
//            });
//        }


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
