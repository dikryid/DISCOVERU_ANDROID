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

public class Menu2 extends AppCompatActivity {

    private ArrayList<Buttons> data;
    private AdapterButtons data_adapter;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        res = this.getResources();


        data = new ArrayList<Buttons>();
        Buttons list_buttons;

        list_buttons = new Buttons(res.getString(R.string.menu_inspect), res.getString(R.string.info_menu_inspect), res.getDrawable(R.drawable.marker2));
        data.add(list_buttons);

        list_buttons = new Buttons(res.getString(R.string.menu_logout), res.getString(R.string.info_menu_logout), res.getDrawable(R.drawable.logout));
        data.add(list_buttons);

        data_adapter = new AdapterButtons(Menu2.this, data);

        ListView listButtonsMisi = (ListView) Menu2.this.findViewById(R.id.listButton);
        listButtonsMisi.setAdapter(data_adapter);
        listButtonsMisi.setTextFilterEnabled(true);

        listButtonsMisi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch(position){
                    case 0:
                        Intent dashboard = new Intent(Menu2.this, Dashboard.class);
                        Menu2.this.startActivity(dashboard);
                        Menu2.this.finish();
                        break;


                    case 1:
                        Menu2.this.onBackPressed();
                        break;

                    default:
                }

            }
        });


    }

    private void BackToSignIn(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(res.getString(R.string.confirm_logout));
        dialog.setCancelable(false);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent Signin = new Intent(Menu2.this, SignIn.class);
                Menu2.this.startActivity(Signin);
                Menu2.this.finish();

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
        Menu2.this.BackToSignIn();
    }

}
