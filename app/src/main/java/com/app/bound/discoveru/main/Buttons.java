package com.app.bound.discoveru.main;

import android.graphics.drawable.Drawable;

public class Buttons {
    String name_buttons;
    Drawable image_res;
    String info_buttons;


    public Buttons(String name_buttons, String info_buttons, Drawable image_res){
        this.name_buttons = name_buttons;
        this.info_buttons = info_buttons;
        this.image_res = image_res;
    }
}
