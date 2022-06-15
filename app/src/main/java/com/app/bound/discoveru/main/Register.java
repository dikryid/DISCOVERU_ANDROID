package com.app.bound.discoveru.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private ArrayList<Buttons> data;
    private AdapterButtons data_adapter;

    private Resources res;

    private EditText edFirstName;
    private EditText edLastName;
    private EditText edMailAddress;
    private EditText edPassword;
    private EditText edAge;

    private RadioGroup rgGender;
    SQLController dbcon;

    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        res = this.getResources();

        edFirstName = (EditText) this.findViewById(R.id.edFirstName);
        edLastName = (EditText) this.findViewById(R.id.edLastName);
        edMailAddress = (EditText) this.findViewById(R.id.edMailAddress);
        edPassword = (EditText) this.findViewById(R.id.edPassword);
        edAge = (EditText) this.findViewById(R.id.edAge);

        rgGender = findViewById(R.id.rgGender);
        RadioGroup.OnCheckedChangeListener rgGenderChange = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbMale:
                        //Toast.makeText(getApplication(), "Saya Suka Java", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbFemale:
                        //Toast.makeText(getApplication(), "Saya Suka Kotlin", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        rgGender.setOnCheckedChangeListener(rgGenderChange);



        Button btnSignin = (Button) this.findViewById(R.id.btn_signin);
        View.OnClickListener btnSigninClick = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent signin = new Intent(Register.this, SignIn.class);
                Register.this.startActivity(signin);
                Register.this.finish();

            }
        };
        btnSignin.setOnClickListener(btnSigninClick);


        Button btnRegister = (Button) this.findViewById(R.id.btn_register);
        View.OnClickListener btnRegisterClick = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                try {
                    dbcon = new SQLController(Register.this);
                    dbcon.open();

                    Member member = new Member();
                    member.first_name = edFirstName.getText().toString().trim();
                    member.last_name = edLastName.getText().toString().trim();
                    member.email_address = edMailAddress.getText().toString().trim();

                    String password = edPassword.getText().toString();
                    String encryptedString = AES192.encrypt(password);

                    member.password = encryptedString;

                    try {
                        member.age = Integer.parseInt(edAge.getText().toString());
                    } catch (Exception e) {
                        member.age = 0;

                        Toast.makeText(Register.this, res.getString(R.string.warn_age), Toast.LENGTH_LONG);
                    }

                    if (rgGender.getCheckedRadioButtonId() == rgGender.getChildAt(0).getId()) {
                        member.gender = "male";
                    }

                    if (rgGender.getCheckedRadioButtonId() == rgGender.getChildAt(1).getId()) {
                        member.gender = "female";
                    }

                    long id = dbcon.searchDatabyEmail(member.email_address);

                    if(id == -1) {
                        long result = dbcon.insertData(member.first_name, member.last_name, member.email_address, member.password, member.age, member.gender);

                        if (result != -1) {
                            Toast.makeText(Register.this, res.getString(R.string.info_register_success), Toast.LENGTH_LONG).show();
                            Register.this.emptyInputFields();
                        } else {
                            Toast.makeText(Register.this, res.getString(R.string.warn_register_failed), Toast.LENGTH_LONG).show();
                            dbcon.close();
                            return;
                        }
                    } else {
                        Toast.makeText(Register.this, res.getString(R.string.warn_email_address), Toast.LENGTH_LONG).show();
                        dbcon.close();
                        return;
                    }

                } catch (Exception e){
                    Toast.makeText(Register.this, res.getString(R.string.warn_register_failed), Toast.LENGTH_LONG).show();
                    dbcon.close();
                    return;
                }



            }
        };
        btnRegister.setOnClickListener(btnRegisterClick);

        this.emptyInputFields();
    }

    private void emptyInputFields(){
        edFirstName.setText("");
        edLastName.setText("");
        edMailAddress.setText("");
        edPassword.setText("");
        edAge.setText("0");
        rgGender.check(R.id.rbMale);

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Register.this,Menu.class);
        Register.this.startActivity(i);
        Register.this.finish();
    }

}
