package com.great.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.great.mycar.model.ser;

import org.w3c.dom.Text;

public class ser_details extends AppCompatActivity {


    TextView title,details,price;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ser_details);
        setId();
    }
    void setId(){
        title=findViewById(R.id.title_template);
        details=findViewById(R.id.details_template);
        price=findViewById(R.id.price);
        title.setText(ser.getTitle());
        details.setText(ser.getDetails());
        price.setText(ser.getPrice());
        image=findViewById(R.id.imageView8);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(ser.getId());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getApplicationContext()).load(uri).into(image);
            }
        });
    }
    public static com.great.mycar.model.ser ser;
}
