package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.CommentAdapter;
import com.great.mycar.adapter.ProductCategoryAdapter;
import com.great.mycar.adapter.myDbAdapter;
import com.great.mycar.model.Comments;
import com.great.mycar.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Comments_Activity extends AppCompatActivity {

    ArrayList<Comments> comments;
    EditText text;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comments_);
        setDetails();
        setComments();
    }
    private void setId(){
        id=getIntent().getStringExtra("id");
    }
    public void setComments() {
        comments=new ArrayList<>();
        setId();
        FirebaseDatabase.getInstance().getReference().child("Comment")
                .child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                comments.add(snapshot.getValue(Comments.class));
                setProductRecycler();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                send();

                return false;
            }
        });
    }
    private void setProductRecycler(){

        RecyclerView list = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
         list.setLayoutManager(layoutManager);
        CommentAdapter comment = new CommentAdapter(this, comments);
         list.setAdapter(comment);

    }
    String mail, User_name, phone;

    private void setDetails() {
        myDbAdapter Db = new myDbAdapter(this);
        User_name = Db.getData_inf()[0];
        mail = Db.getData_inf()[1];
        phone = Db.getData_inf()[2];
        text=findViewById(R.id.editText);
    }

    public void send() {
        if(text.getText().toString().length()-text.getText().toString().split(" ").length
                <text.getText().toString().length()&&text.getText().toString().length()>0
                &&text.getText().toString().split(" ").length>0){
        Comments comment =new Comments("00",User_name,mail,text.getText().toString(),"0");
        FirebaseDatabase.getInstance().getReference().child("Comment").child(id).push().setValue(comment);
            FirebaseDatabase.getInstance().getReference().child("Act")
                    .child(mail.substring(0,mail.length()-4))
                    .child("Comment").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int x=0;
                    if(snapshot.exists())
                        x=Integer.parseInt(snapshot.getValue().toString());
                    FirebaseDatabase.getInstance().getReference().child("Act")
                            .child(mail.substring(0,mail.length()-4))
                            .child("Comment").setValue((x+1)+"");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        text.setText("");
    }
}
