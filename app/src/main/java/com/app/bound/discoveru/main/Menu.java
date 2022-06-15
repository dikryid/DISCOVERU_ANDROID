package com.app.bound.discoveru.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private ArrayList<Buttons> data;
    private AdapterButtons data_adapter;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        res = this.getResources();


        data = new ArrayList<Buttons>();
        Buttons list_buttons;

        list_buttons = new Buttons(res.getString(R.string.menu_signin), res.getString(R.string.info_menu_signin), res.getDrawable(R.drawable.signin));
        data.add(list_buttons);

        list_buttons = new Buttons(res.getString(R.string.menu_register), res.getString(R.string.info_menu_register), res.getDrawable(R.drawable.register));
        data.add(list_buttons);

        list_buttons = new Buttons(res.getString(R.string.menu_exit), res.getString(R.string.info_menu_exit), res.getDrawable(R.drawable.exit));
        data.add(list_buttons);

        data_adapter = new AdapterButtons(Menu.this, data);

        ListView listButtonsMisi = (ListView) Menu.this.findViewById(R.id.listButton);
        listButtonsMisi.setAdapter(data_adapter);
        listButtonsMisi.setTextFilterEnabled(true);

        listButtonsMisi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch(position){
                    case 0:
                        Intent Signin = new Intent(Menu.this, SignIn.class);
                        Menu.this.startActivity(Signin);
                        Menu.this.finish();
                        break;

                    case 1:
                        Intent Register = new Intent(Menu.this, Register.class);
                        Menu.this.startActivity(Register);
                        Menu.this.finish();
                        break;

                    case 2:
                        Menu.this.onBackPressed();
                        break;

                    default:
                }

            }
        });


    }

    private void BackToExit(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(res.getString(R.string.confirm_exit_app));
        dialog.setCancelable(false);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed(){
        Menu.this.BackToExit();
    }

}
