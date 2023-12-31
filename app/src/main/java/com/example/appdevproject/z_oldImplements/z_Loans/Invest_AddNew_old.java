package com.example.appdevproject.z_oldImplements.z_Loans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdevproject.Investment.Invest_Page;
import com.example.appdevproject.R;

public class Invest_AddNew_old extends AppCompatActivity {
    Button saveBtn;
    EditText iname,itype,iamount;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_investment);

        saveBtn = findViewById(R.id.btnInvesSave);
        iname = findViewById(R.id.InvesName);
        itype = findViewById(R.id.InvesType);
        iamount = findViewById(R.id.InvesAmount);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = String.valueOf(1);
                String name = iname.getText().toString()+"\n";
                String type = itype.getText().toString();
                String amount = iamount.getText().toString();

                Invest_Db_old dbHandler = new Invest_Db_old(Invest_AddNew_old.this);

                dbHandler.insertInvestments(userId, name,type,amount);
                Toast.makeText(getApplicationContext(), "New Investment Added Successfully!",Toast.LENGTH_LONG).show();
                intent = new Intent(Invest_AddNew_old.this, Invest_Page.class);
                startActivity(intent);
            }
        });

    }
}