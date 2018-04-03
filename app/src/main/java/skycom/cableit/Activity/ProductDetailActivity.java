package skycom.cableit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


import skycom.cableit.Classes.Product;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ProductDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String productIDString = "";
    int productID = 0;
    String tempProName = "";
    String tempProNo = "";
    String tempProDescription = "";
    String tempProCost = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        int tempProductID;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                productIDString = null;
            } else {
                productIDString = extras.getString("PRODUCT_ID");
            }
        } else {
            productIDString = (String) savedInstanceState.getSerializable("PRODUCT_ID");
        }

        if (productIDString != null)
        {
            if (productIDString != "") {
                tempProductID = Integer.parseInt(productIDString) + 1;
                productID = tempProductID;




            }
        }
        List<Product> productOne = database.productDAO().getProduct(productID);



        TextView editProName = (TextView) findViewById(R.id.txtProName);
        TextView editProNo = (TextView) findViewById(R.id.txtProNo);
        TextView editProDescription = (TextView)findViewById(R.id.txtProDescription);
        TextView editProCost = (TextView) findViewById(R.id.txtProCost);


        editProName.setText(productOne.get(0).productName.toString());
        editProNo.setText(productOne.get(0).partNo.toString());
        editProDescription.setText(productOne.get(0).description.toString());
        editProCost.setText(productOne.get(0).productCost.toString());


//        Button btnProductList = findViewById(R.id.btnSaveProduct);
//        btnProductList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}
