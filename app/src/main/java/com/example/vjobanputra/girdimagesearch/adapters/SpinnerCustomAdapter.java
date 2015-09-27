package com.example.vjobanputra.girdimagesearch.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vjobanputra.girdimagesearch.R;

public class SpinnerCustomAdapter extends ArrayAdapter<String> {

    public SpinnerCustomAdapter(Context context, String[] strings) {
        super(context, R.layout.spinner_item, strings);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ((TextView) v).setGravity(Gravity.RIGHT);
        return v;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setGravity(Gravity.RIGHT);
        return v;
    }
}