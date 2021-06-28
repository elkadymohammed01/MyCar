package com.great.mycar.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

public class GetTrueImage implements OnSuccessListener<Uri> {
    private static Integer last;
    Integer pos,val=4;
    ImageView image;
    Context context;
    public GetTrueImage(Integer pos, ImageView image, Context context) {
        this.pos = pos;
        this.image = image;
        this.context = context;
    }

    @Override
    public void onSuccess(Uri uri) {
        if(Math.abs(pos-last)<val)
        Glide.with(context).load(uri).into(image);
    }
    public static void setLastOne(Integer l){
        last=l;
    }
}
