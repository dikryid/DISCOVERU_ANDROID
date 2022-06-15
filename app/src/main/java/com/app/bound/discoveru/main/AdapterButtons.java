package com.app.bound.discoveru.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterButtons extends BaseAdapter {

    private static LayoutInflater inflater=null;
    private View vi;
    private ArrayList<Buttons> data;

    public AdapterButtons(Activity activity, ArrayList<Buttons> data) {
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.format_list_view_buttons, null);


        TextView txtButtons = (TextView) vi.findViewById(R.id.txtButtons);
        TextView txtInfo = (TextView) vi.findViewById(R.id.txtInfo);
        ImageView imgList = (ImageView) vi.findViewById(R.id.imgList);

        Buttons list = (Buttons) this.getItem(position);

        txtButtons.setText(list.name_buttons);
        txtInfo.setText(list.info_buttons);
        imgList.setImageDrawable(list.image_res);

        return vi;
    }
}
