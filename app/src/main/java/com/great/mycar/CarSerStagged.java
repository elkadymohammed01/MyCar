package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.QuestionAdapter;
import com.great.mycar.adapter.SerAdapter;
import com.great.mycar.model.Question;
import com.great.mycar.model.ser;

import java.util.ArrayList;
import java.util.List;

public class CarSerStagged extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_car_ser_stagged);
        setList();
    }
    List<ser> serList=new ArrayList<>();
    private void setList(){
        FirebaseDatabase.getInstance().getReference().child("ser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    serList.add(data.getValue(ser.class));
                }
                setProdRecycler(serList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    RecyclerView prodItemRecycler;
    private void setProdRecycler(List<ser>sers){
        prodItemRecycler =findViewById(R.id.rv_car);
        prodItemRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SerAdapter adapter =new SerAdapter(this,sers);
        prodItemRecycler.setAdapter(adapter);

    }
}
