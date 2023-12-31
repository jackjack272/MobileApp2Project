package com.example.appdevproject.Investment.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdevproject.Investment.Models.Invest_Debt;
import com.example.appdevproject.R;
import com.example.appdevproject.DataBase.ProjectDb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Invest_fragmentBond#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Invest_fragmentBond extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public Invest_fragmentBond() {
        // Required empty public constructor
    }
    public static Invest_fragmentBond newInstance(String param1, String param2) {
        Invest_fragmentBond fragment = new Invest_fragmentBond();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invest__debt, container, false);
    }

//------------------ coding area
        //bonds and debt are the same object but reversed at a value.
    // making a seperate interface to make it easier to debug and work with later.
    // ill copy and paste and change the borrowed value to +

//coding area.

    EditText debtName, amountBorrowed, interestRate, compoundsPerYear, monthsOnLoan;
    Button saveBtn;
    ProjectDb projectDb;

    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState){
        makeAssocications();

//        admin_setValues();


        //save a debt to the db
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Invest_Debt newDebt= createNewDebt();
                if(newDebt ==null){
                    return;
                }
                newDebt.setForeinKey(getForeinKey());
                newDebt.setIsDebt(false);

                projectDb.debt_makeOne(newDebt);
                Toast.makeText(getContext(), "added a new debt", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void admin_setValues(){
        debtName.setText("Bond");
        amountBorrowed.setText("1000");
        interestRate.setText("10");
        compoundsPerYear.setText("2");
        monthsOnLoan.setText("24");
    }

    private void makeAssocications(){
        debtName= getView().findViewById(R.id.invest_debt_Name);
        amountBorrowed= getView().findViewById(R.id.invest_debt_getAmountBorrowed);
        interestRate= getView().findViewById(R.id.invest_debt_getInterestRate);
        compoundsPerYear=getView().findViewById(R.id.invest_debt_getCompoundsPerYear);
        monthsOnLoan=getView().findViewById(R.id.invest_debt_getTimeInMonths);

        saveBtn=getView().findViewById(R.id.invest_debt_btn);

        projectDb= new ProjectDb(getContext());

    //this isent used any where else and is for the consmetic
        TextView heading= getView().findViewById(R.id.invest_debt_heading);
        heading.setText(getString(R.string.invest_bond_heading));
    }

    private Invest_Debt createNewDebt(){

        Double amountBorrowed, interestRate;
        Integer compounds, loanterm;
        String name= debtName.getText().toString();

        Boolean badValues=false;
        try{
            amountBorrowed= Double.parseDouble(this.amountBorrowed.getText().toString() );
        }catch (Exception e){
            this.amountBorrowed.setText("");
            this.amountBorrowed.setHint("bad amount borrowed");
            badValues=true;
            amountBorrowed=0.0;
        }

        try{
            interestRate= Double.parseDouble( this.interestRate.getText().toString());
        }catch (Exception e){
            this.interestRate.setText("");
            this.interestRate.setHint("bad interest rate");
            badValues=true;
            interestRate=0.0;
        }

        try{
            compounds= Integer.parseInt( this.compoundsPerYear.getText().toString());
        }catch (Exception e){
            this.compoundsPerYear.setText("");
            this.compoundsPerYear.setHint("bad compounds");
            badValues=true;
            compounds=0;
        }

        try{
            loanterm= Integer.parseInt(this.monthsOnLoan.getText().toString());
        }catch (Exception e){
            this.monthsOnLoan.setHint("");
            this.monthsOnLoan.setHint("bad loan term");
            badValues=true;
            loanterm=0;
        }


        if(badValues){
            Toast.makeText(getContext(), "Please correct the indicated values", Toast.LENGTH_SHORT).show();
        }else {
            if(amountBorrowed >0 && interestRate> 0 &&
                    compounds>0 && loanterm >0){

                //bonds are positive.
                return new Invest_Debt(name,amountBorrowed, interestRate, compounds, loanterm);
            }
        }
        return  null;
    }

    private Integer getForeinKey(){
        SharedPreferences sharedPreferences= requireContext()
                .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString("username","");
        Integer id= projectDb.getUserById(userName);
        return id;
    }

}