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
import com.great.mycar.Comments_Activity;
import com.great.mycar.R;
import com.great.mycar.model.Products;
import com.great.mycar.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ProductViewHolder> {

    Context context;
    List<Question>questionList;
    LottieAnimationView lottie;
    public QuestionAdapter(Context context, List<Question> questionList,LottieAnimationView lottie) {
        this.context = context;
        this.questionList = questionList;
        this.lottie=lottie;
        setDetails();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.problem, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        final Question q=questionList.get(position);

        holder.name.setText(q.getName());
        holder.email.setText(q.getEmail());
        holder.details.setText(q.getDetails());
        holder.love.setText(q.getLove());
        holder.imageCar.setImageResource(R.color.gray);
        holder.comments.setText(q.getComments());

        //get User Image from FireBase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(q.getEmail());
        GetTrueImage GLV=new GetTrueImage(position,holder.user,context);
        islandRef.getDownloadUrl().addOnSuccessListener(GLV);

        //get Car Image from FireBase
        FirebaseStorage sol = FirebaseStorage.getInstance();
        StorageReference rol = sol.getReference();
        StorageReference pol = rol.child(q.getId()+".png");
        GetTrueImage GTI=new GetTrueImage(position,holder.imageCar,context);
        GetTrueImage.setLastOne(position);
        pol.getDownloadUrl().addOnSuccessListener(GTI);


        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, Comments_Activity.class);
                intent.putExtra("id",q.getId());
                context.startActivity(intent);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            int click=0;
            @Override
            public void onClick(View v) {
                click++;
                if (click==2){
                    FirebaseDatabase.getInstance().getReference().child("LoveList")
                            .child(q.getId()).child(mail.substring(0,mail.length()-4))
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){

                                lottie.setVisibility(View.VISIBLE);
                                lottie.playAnimation();
                            FirebaseDatabase.getInstance().getReference().child("Question")
                                    .child(questionList.get(position).getId()).child("love")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Integer val=Integer.parseInt(snapshot.getValue().toString());
                                            val+=1;
                                            UpVote(position);
                                            FirebaseDatabase.getInstance().getReference().child("Question")
                                                    .child(questionList.get(position).getId()).child("love")
                                                    .setValue(val+"");
                                            questionList.get(position).setLove(val+"");
                                            holder.love.setText(val+"");
                                            FirebaseDatabase.getInstance().getReference().child("LoveList")
                                                    .child(q.getId()).child(mail.substring(0,mail.length()-4))
                                                    .setValue(1);
                                            lottie.setVisibility(View.INVISIBLE);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }

                                    });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
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
    private void UpVote(final Integer id){
        FirebaseDatabase.getInstance().getReference().child("Act")
                .child(questionList.get(id).getEmail().substring(0,questionList.get(id).getEmail().length()-4))
                .child("UpVote").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    FirebaseDatabase.getInstance().getReference().child("Act")
                            .child(questionList.get(id)
                                    .getEmail().substring(0,questionList.get(id).getEmail().length()-4))
                            .child("UpVote").setValue((Integer.parseInt(snapshot.getValue().toString())+1)+"");
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Act")
                            .child(questionList.get(id).getEmail()
                                    .substring(0,questionList.get(id).getEmail().length()-4))
                            .child("UpVote").setValue("1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView name,email,details;
        ImageView imageCar,user;
        TextView  love, comments;
        View view;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.mail);
            details=itemView.findViewById(R.id.details);
            imageCar=itemView.findViewById(R.id.image);
            user=itemView.findViewById(R.id.profile_image);
            love=itemView.findViewById(R.id.love);
            comments=itemView.findViewById(R.id.comments);
            view=itemView;
        }
    }

}
