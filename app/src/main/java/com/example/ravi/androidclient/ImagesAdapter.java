package com.example.ravi.androidclient;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends ArrayAdapter<Images> {
    private Context mContext;
    private List<Images> imagesList = new ArrayList<>();

    public ImagesAdapter(@NonNull Context context, ArrayList<Images> list) {
        super(context, 0 , list);
        mContext = context;
        imagesList = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.images_list_item,parent,false);

        Images currentImage = imagesList.get(position);


        ImageView imageView = (ImageView) listItem.findViewById(R.id.image_item);


        Picasso.with(mContext).load(currentImage.getUrl()).fit().into(imageView);

        return listItem;
    }
}
