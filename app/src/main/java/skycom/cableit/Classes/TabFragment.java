package skycom.cableit.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import skycom.cableit.R;


public class TabFragment extends Fragment{

    int position;
    private TextView textView;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textView);

        textView.setText("Fragment " + (position + 1));


    }

   //     public void myClickMethod(View v) {
//            switch(v.getId()) {
//                // Just like you were doing
//            }
//        };
//
//    @Override
//    public void onClick(View view) {
//        switch(view.getId()) {
//            // Just like you were doing
//        }
//
//    }

//        Button btnAddAddress = findViewById(R.id.btnNewAddress);
//        btnAddAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddressEditDetailActivity.class);
//                if (companyIDString != ""){
//                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
//                    startActivity(intent);
//                }
//            }
//        });



}