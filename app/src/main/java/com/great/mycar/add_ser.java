package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.great.mycar.model.ser;

public class add_ser extends AppCompatActivity {

    int id=0;
    EditText name,details,price;
    ImageView image;
    TextView post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ser);
        setId();
    }
    public void setId() {
        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        details=findViewById(R.id.details);
        price=findViewById(R.id.price);
        id=0;
        post=findViewById(R.id.post);
        editText(name,0);
        editText(details,1);
        editText(price,2);
    }

    boolean show[]=new boolean[10];
    private void editText(EditText ed, final int index){
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().split(" ").length>0&&
                        !s.toString().equals("")&&s.toString().length()>0){
                    if(!show[index])
                        id++;
                    show[index]=true;
                }
                else{
                    if(show[index])
                        id--;
                    show[index]=false;
                }
                if(id>=4){
                    post.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                    post.setTextColor(getResources().getColor(R.color.gray));
            }
        });
    }
    public void add_image(View view) {
        get_data(new String[]{"image/*"});
    }
    void get_data(String[] lop){
        try {
            String[] mimeTypes =
                    lop;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 100);

        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                if(!show[4])
                    id++;
                show[4]=true;
                if(id>=4){
                    post.setTextColor(getResources().getColor(R.color.blue));
                }
                Glide.with(getApplicationContext()).load(file).into(image);
            }
        }
    }

    Uri file;
    private void upload_file(String f){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child(f);
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }

    public void post(View view) {
        ser ser=new ser();
        ser.setId(System.currentTimeMillis()+"");
        ser.setTitle(name.getText().toString());
        ser.setDetails(details.getText().toString());
        ser.setPrice(price.getText().toString());
        if(id>=4){
            FirebaseDatabase.getInstance().getReference().child("ser")
                    .child(ser.getId()).setValue(ser);
            upload_file(ser.getId());}

    }
}
