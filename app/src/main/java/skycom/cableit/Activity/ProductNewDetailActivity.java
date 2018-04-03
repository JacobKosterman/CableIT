package skycom.cableit.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import skycom.cableit.Classes.Product;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;



public class ProductNewDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    Context context = this;
    String tempProNo = "";
    String tempProName = "";
    String tempProDescription = "";
    double tempProCost = 0.0;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        Button btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editProNo = (EditText)findViewById(R.id.txtProNo);
                EditText editProName = (EditText)findViewById(R.id.txtProName);
                EditText editProDescription  = (EditText)findViewById(R.id.txtProDescription);
                EditText editProCost = (EditText)findViewById(R.id.txtProCost);

                tempProNo = editProNo.getText().toString();
                tempProName = editProName.getText().toString();
                tempProDescription = editProDescription.getText().toString();

                if (editProCost.getText().toString().isEmpty()){
                    tempProCost = 0.0;
                }else{

                    tempProCost =  Double.parseDouble(editProCost.getText().toString());
                }



                    List<Product> ProductCheckList = database.productDAO().getProductByNumber(tempProNo);

                    if (ProductCheckList.isEmpty()){

                        addProductAndDivert();

                    }else {

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        builder2.setMessage("Part already exists. Create anyways?");
                        builder2.setCancelable(true);

                        builder2.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        addProductAndDivert();
                                        dialog.cancel();
                                    }
                                });

                        builder2.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder2.create();
                        alert11.show();

                    }
                }


        });
    }
    public void addProductAndDivert(){

        Product product = new Product(1, tempProName, tempProNo, tempProDescription,
                tempProCost, 0.0, 0.0, true);
        database.productDAO().addProduct(product);
        List<Product> ProductCheckList = database.productDAO().getProductByNumber(tempProNo);

        if (ProductCheckList.size() == 1){

//            Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
//            int id = ProductCheckList.get(0).id;
//
//            intent.putExtra("NEW_PRODUCT_ID", String.valueOf(id));
//            startActivity(intent);

        } else {

            Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}
