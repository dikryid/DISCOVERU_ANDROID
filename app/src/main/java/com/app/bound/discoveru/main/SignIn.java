package com.app.bound.discoveru.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import java.util.ArrayList;

public class SignIn extends AppCompatActivity {

    private ArrayList<Buttons> data;
    private AdapterButtons data_adapter;

    private Resources res;
    SQLController dbcon;

    private EditText edUserName;
    private EditText edPassword;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        global = (Global)this.getApplication();
        res = this.getResources();

        edUserName= (EditText)this.findViewById(R.id.edUser);
        edPassword = (EditText)this.findViewById(R.id.edPassword);

        Button btnSignin = (Button) this.findViewById(R.id.btn_signin);
        View.OnClickListener btnSigninClick = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String user = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                dbcon = new SQLController(SignIn.this);
                dbcon.open();


                long id = dbcon.searchUserbyEmail(user, password);

                if(id == -1){
                    Toast.makeText(SignIn.this, res.getString(R.string.warn_user_not), Toast.LENGTH_LONG).show();
                    dbcon.close();
                    return;
                } else {
                    Cursor c = dbcon.getDatabyId(id);
                    if(c!=null){
                        Member member = new Member();

                        member.first_name = c.getString(1);
                        member.last_name = c.getString(2);
                        member.email_address = c.getString(3);
                        member.password = c.getString(4);
                        member.age = c.getInt(5);
                        member.gender = c.getString(6);

                        global.member = member;
                    } else {
                        global.member = null;
                    }

                    dbcon.close();

                    Intent menu2 = new Intent(SignIn.this, Menu2.class);
                    SignIn.this.startActivity(menu2);
                    SignIn.this.finish();
                }

            }
        };
        btnSignin.setOnClickListener(btnSigninClick);


        Button btnRegister = (Button) this.findViewById(R.id.btn_register);
        View.OnClickListener btnRegisterClick = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent register = new Intent(SignIn.this, Register.class);
                SignIn.this.startActivity(register);
                SignIn.this.finish();

            }
        };
        btnRegister.setOnClickListener(btnRegisterClick);


    }



    @Override
    public void onBackPressed(){
        Intent menu = new Intent(SignIn.this,Menu.class);
        SignIn.this.startActivity(menu);
        SignIn.this.finish();
    }

}
