package com.example.vjobanputra.girdimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vjobanputra.girdimagesearch.Models.Image;
import com.example.vjobanputra.girdimagesearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vjobanputra on 9/25/15.
 */
public class ImageArrayAdapter extends ArrayAdapter<Image> {

    public ImageArrayAdapter(Context context, List<Image> images) {
        super(context, R.layout.one_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_image, parent, false);
        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(image.getThumbnail()).into(ivImage);
        tvTitle.setText(Html.fromHtml(image.getTitle()));

        return convertView;
    }
}
