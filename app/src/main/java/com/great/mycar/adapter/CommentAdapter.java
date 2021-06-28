package com.great.mycar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.great.mycar.MapsActivity;
import com.great.mycar.R;
import com.great.mycar.model.Comments;
import com.great.mycar.model.Question;
import com.great.mycar.ser_details;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ProductViewHolder> {

    Context context;
    List<Comments>commentList;
    LottieAnimationView lottie;
    public CommentAdapter(Context context, List<Comments> commentList) {
        this.context = context;
        this.commentList = commentList;
        setDetails();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        Comments comment =commentList.get(position);
        holder.name.setText(comment.getName());
        holder.details.setText(comment.getDetails());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(comment.getEmail());
        holder.user.setImageResource(R.color.gray);
        GetTrueImage.setLastOne(position);
        GetTrueImage GLV=new GetTrueImage(position,holder.user,context);
        GLV.val=8;
        islandRef.getDownloadUrl().addOnSuccessListener(GLV);

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
        return commentList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView name,details;
        ImageView user;
        TextView  love;
        View view;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            name=itemView.findViewById(R.id.name);
            details=itemView.findViewById(R.id.details);
            user=itemView.findViewById(R.id.profile_image);
        }
    }

}
