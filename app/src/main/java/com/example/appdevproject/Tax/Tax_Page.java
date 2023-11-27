package com.example.appdevproject.Tax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appdevproject.Investment.Models.Totals_Save;
import com.example.appdevproject.R;
import com.example.appdevproject.DataBase.ProjectDb;
import com.example.appdevproject.Tax.Adapters.Tax_InvestRecyAdapter;
import com.example.appdevproject.Tax.Adapters.Tax_LabourAdapter;
import com.example.appdevproject.Tax.Fab.Tax_AddNewWage;
import com.example.appdevproject.Tax.Models.Tax_Income;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Tax_Page extends AppCompatActivity {

    private TextView labourFor, investFor, govWants;
    private RecyclerView labour_recycle, invest_recycle;
    private FloatingActionButton fab;
    private ProjectDb myDb;

    private RecyclerView.LayoutManager layoutManager, labourManager;


    private Tax_InvestRecyAdapter recyAdapter;
    private Tax_LabourAdapter labourAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        makeAssocications();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tax_AddNewWage.newInstace()
                    .show(
                        getSupportFragmentManager(),
                        Tax_AddNewWage.TAG
                    );
            }
        });


    //top recycler
        makeLabourAdapter();
    //bottom recycler
        makeInvestedAdapter();

        makeHeadings();


    }


    public void makeLabourAdapter(){
        //not displaying
        List<Tax_Income> items= myDb.income_readAll(getForeighnkey());

        labourManager= new LinearLayoutManager(this);
        labour_recycle.setLayoutManager(labourManager);

        labourAdapter= new Tax_LabourAdapter();
        labourAdapter.setMyItems(items);
        labour_recycle.setAdapter(labourAdapter);

    }

    public void makeInvestedAdapter(){
        List<Totals_Save> myTaxableItems= myDb.totals_readTotal(getForeighnkey());

        layoutManager= new LinearLayoutManager(this);
        invest_recycle.setLayoutManager(layoutManager);

        recyAdapter= new Tax_InvestRecyAdapter();
        recyAdapter.setMyItems(myTaxableItems);
        invest_recycle.setAdapter(recyAdapter);
    }


    private void makeHeadings(){
        labourFor.setText(String.format("My labour: %.2f. My prize: %.2f",
                labourAdapter.getYearlyIncome(), labourAdapter.getTotalLabourTax()));
        investFor.setText(String.format("I gained: %.2f",recyAdapter.getNetTax()));
        govWants.setText(String.format("Gov's cut: $%.2f", labourAdapter.getTotalLabourTax()+ recyAdapter.getNetTax()));
    }

    private void makeAssocications(){
//    textviews
        labourFor= findViewById(R.id.inc_labour);
        investFor= findViewById(R.id.inc_invest);
        govWants= findViewById(R.id.inc_govWants);

    //recyclers
        labour_recycle= findViewById(R.id.inc_labourRecycle);
        invest_recycle=findViewById(R.id.inc_investRecycle);

    //extra
        myDb= new ProjectDb(Tax_Page.this);
        fab= findViewById(R.id.inc_fab);
    }


    public int getForeighnkey() {
        SharedPreferences s=getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int foreignKey= myDb.getUserById(s.getString("username",""));
        return foreignKey;
    }


}