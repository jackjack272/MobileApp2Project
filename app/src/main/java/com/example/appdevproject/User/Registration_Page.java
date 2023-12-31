package com.example.appdevproject.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

//<<<<<<< HEAD:app/src/main/java/com/example/appdevproject/Pages/RegistrationPage.java
import com.example.appdevproject.Navigation.Landing_Page;
import com.example.appdevproject.R;
import com.example.appdevproject.DataBase.ProjectDb;
import com.example.appdevproject.User.Models.User;
//=======
//import com.example.appdevproject.CustomException.MissingField;
//import com.example.appdevproject.LandingPage.IntroActivity;
//import com.example.appdevproject.LandingPage.LandingPage;
//import com.example.appdevproject.User.UserDb;
//>>>>>>> 33761130263a0631cbbdc82cebe34b13e61f2660:app/src/main/java/com/example/appdevproject/RegistrationPage.java


public class Registration_Page extends AppCompatActivity {
    private static final String TAG= Registration_Page.class.getSimpleName();

    // i want a menue on the left hand side with all the avialble activities.

    /**
     *      The user is being saved to the database , but i need to check if the username exists
     *          username is going to be PK for the db     *
     */


    //the log in and registration dosent work properly ill have to ask prof for help
        // the id throws an error immediately, why?

    // how do i secure the db from sql injection attacks?

    private  ProjectDb projectDb;
    private EditText userName, password, email, dateOfBirth;
    private Switch toggle;
    private Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        makeAssociates();

//        admin_prePopulate("smith jones");
//        admin_quickLogIn("smith jones"); // this one needs to exists


        projectDb =new ProjectDb(Registration_Page.this);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    nextPage.setText("Login");
                    email.setVisibility(View.INVISIBLE);
                    dateOfBirth.setVisibility(View.INVISIBLE);
                    //log in
                }else{
                    nextPage.setText("Register");
                    email.setVisibility(View.VISIBLE);
                    dateOfBirth.setVisibility(View.VISIBLE);
                }
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = null;

                if(toggle.isChecked()){
                    //login
                    String userToLogIn=userName.getText().toString();
                    String passWord=password.getText().toString();

                    if(userToLogIn.equals("")){
                        return;
                    } //check for empty.

                    //get the user.
                    user= projectDb.getUserByUsername(userToLogIn);
                    if(user ==null){
                        Toast.makeText(Registration_Page.this, "This user dosent exist", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(! User.comparePasswords(passWord, user.getPassword())){
                        Toast.makeText(Registration_Page.this, "user or password is wrong", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                else{
                    email.setVisibility(View.VISIBLE);
                    dateOfBirth.setVisibility(View.VISIBLE);
                    nextPage.setText("Register!");


                    //register
                    user= getUserRegistrationObject();
                    User x= projectDb.getUserByUsername(user.getUserName());
                    if(x ==null){

                        user.setPassword(User.md5HashEncrypt(user.getPassword()) );
                        projectDb.makeUser(user);

                    }else{
                        Toast.makeText(Registration_Page.this, "This username exits, pick another one", Toast.LENGTH_SHORT).show();
                        return;// user exists so kill control
                    }
                }

                //save the username
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("username",user.getUserName());
                myEdit.apply();

                // go next intent.
                Toast.makeText(Registration_Page.this, "Success! Welcome in!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Registration_Page.this, Landing_Page.class));
            }
        });

    }

    private void admin_quickLogIn(String logInUser){
        //save the username
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("username",logInUser);
        myEdit.apply();

        // go next intent.
        Toast.makeText(Registration_Page.this, "Success! Welcome in!", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(RegistrationPage.this, com.example.appdevproject.z_oldImplements.z_Loans.LandingPage.class));
        startActivity(new Intent(Registration_Page.this, Landing_Page.class));

    }

    private void admin_prePopulate(String logInUser){
        userName.setText(logInUser);
        password.setText("apple1");
        email.setText("james@gmail.com");
        dateOfBirth.setText("2222");


    }

    private void makeAssociates(){
        userName=findViewById(R.id.getUserName);
        password=findViewById(R.id.getPassword);
        email=findViewById(R.id.getEmail);
        dateOfBirth=findViewById(R.id.getDateOfBirth);
        toggle=findViewById(R.id.switch1);

        nextPage=findViewById(R.id.nextPage);

    };

    private User getUserRegistrationObject()  {
        String msg="please fill me";
        Boolean filledFields=true;


        if( String.valueOf(userName.getText()).equals("") ){
            userName.setHint(msg);
            filledFields=false;
        } else if (String.valueOf( password.getText()).equals("")) {
            password.setHint(msg);
            filledFields=false;
        }else if(String.valueOf( email.getText()).equals("")){
            email.setHint(msg);
            filledFields=false;
        } else if (String.valueOf( dateOfBirth.getText()).equals("")) {
            dateOfBirth.setHint(msg);
            filledFields=false;
        }

        if(filledFields) {
            return new User(
                    String.valueOf(userName.getText()),
                    String.valueOf(password.getText()),
                    String.valueOf(email.getText()),
                    String.valueOf(dateOfBirth.getText())
            );
        }
        return null;
    }



}