package com.great.mycar.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.great.mycar.R;
import com.great.mycar.model.ser;

import java.util.List;

public class SerAdapter extends RecyclerView.Adapter<SerAdapter.ProductViewHolder> {

    Context context;
    List<ser> serList;
    public SerAdapter(Context context, List<ser> commentList) {
        this.context = context;
        this.serList = commentList;
        setDetails();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_frutorials, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        ser ser = serList.get(position);
        holder.name.setText(ser.getTitle());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(ser.getId());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.image);
            }
        });
    }
    String mail, User_name, phone;

    private void setDetails() {
        myDbAdapter Db = new myDbAdapter(context);
        User_name = Db.getData_inf()[0];
        mail = Db.getData_inf()[1];
        phone = Db.getData_inf()[2];
    }
    @Override
    public int getItemCount() {
        return serList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;
        View view;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            name=itemView.findViewById(R.id.title);
            image=itemView.findViewById(R.id.image);
        }
    }

}
