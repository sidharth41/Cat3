package com.sid.cat3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Data extends AppCompatActivity {
    SharedPreferences preferance;
    EditText t1,t2,t3;

    Button sharedbutton,viewdata,sql,Firebase,Viewsql,viewfb;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        t1=findViewById(R.id.name);
        t2=findViewById(R.id.password);
        t3=findViewById(R.id.Nameid);
        sharedbutton=findViewById(R.id.save);
        sql=findViewById(R.id.SQL);
        viewdata=findViewById(R.id.View);
        Viewsql=findViewById(R.id.ViewSQL);
        Firebase=findViewById(R.id.Firebase);
        viewfb=findViewById(R.id.ViewFirebase);
        preferance=getSharedPreferences("Mypreferance",MODE_PRIVATE);
        editor=preferance.edit();

        Firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Sample").child(t1.getText().toString());
                databaseReference.child("Name").setValue(t1.getText().toString());
                databaseReference.child("Password").setValue(t2.getText().toString());
            }
        });
        viewfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Sample").child(t1.getText().toString());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child(t3.getText().toString()).child("Name").getValue().toString();
                        String password=snapshot.child(t3.getText().toString()).child("Password").getValue().toString();
                        Toast.makeText(Data.this, name+" "+password, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        sharedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("User_Name",t1.getText().toString());
                editor.putString("User_Password",t2.getText().toString());
                editor.commit();
                Toast.makeText(Data.this, "Data stored successfully...", Toast.LENGTH_SHORT).show();
            }
        });
        viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Data.this, preferance.getString("User_Name","")+" "+preferance.getString("User_Password",""), Toast.LENGTH_SHORT).show();

            }
        });
        DBhelper helper=new DBhelper(Data.this);
        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    helper.addData(t1.getText().toString(), t2.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(Data.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Viewsql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Data.this, ""+helper.getData(t3.getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });



    }
}