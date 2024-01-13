package com.example.sqlitedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerview;

    TextView textview;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById(R.id.recyclerview);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = databaseHelper.gerdata();
        if (cursor !=null && cursor.getCount()>0){

            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String roll = cursor.getString(2);
                String father_name = cursor.getString(3);
                String mother_name = cursor.getString(4);
                String clas = cursor.getString(5);
                String address = cursor.getString(6);
                byte[] imageData = cursor.getBlob(7 );
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);


                hashMap = new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("name",""+name);
                hashMap.put("roll",""+roll);
                hashMap.put("father_name",""+father_name);
                hashMap.put("mother_name",""+mother_name);
                hashMap.put("clas",""+clas);
                hashMap.put("address",""+address);
                hashMap.put("image",""+address);
                hashMap.put("image", bitmapToBase64(bitmap));
                arrayList.add(hashMap);

            }
        }


        My_adapter myAdapter = new My_adapter();
        recyclerview.setAdapter(myAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));



    }


    /*
            HashMap<String,String> hashMap1 = arrayList.get(position);


            String sid = hashMap1.get("id");
            String sname = hashMap1.get("name");
            String sroll = hashMap1.get("roll");
            String sfather_name = hashMap1.get("father_name");
            String smother_name = hashMap1.get("mother_name");
            String sclas = hashMap1.get("clas");
            String saddress = hashMap1.get("address");
     */


    public class My_adapter extends RecyclerView.Adapter<My_adapter.ViewHolder>{

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView name,roll,clas,father,mother,address;
            CircleImageView profile_image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                roll = itemView.findViewById(R.id.roll);
                clas = itemView.findViewById(R.id.clas);
                father = itemView.findViewById(R.id.father);
                mother = itemView.findViewById(R.id.mother);
                address = itemView.findViewById(R.id.address);
                profile_image = itemView.findViewById(R.id.profile_image);
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE);
            View myview = layoutInflater.inflate(R.layout.layout,parent,false);



            return new ViewHolder(myview);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HashMap<String,String> hashMap1 = arrayList.get(position);


            String sid = hashMap1.get("id");
            String sname = hashMap1.get("name");
            String sroll = hashMap1.get("roll");
            String sfather_name = hashMap1.get("father_name");
            String smother_name = hashMap1.get("mother_name");
            String sclas = hashMap1.get("clas");
            String saddress = hashMap1.get("address");
            String simage = hashMap1.get("image");


            holder.name.setText(sname);
            holder.roll.setText(sroll);
            holder.father.setText(sfather_name);
            holder.mother.setText(smother_name);
            holder.clas.setText(sclas);
            holder.address.setText(saddress);
            Bitmap decodedImage = base64ToBitmap(simage);


            holder.profile_image.setImageBitmap(decodedImage);


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}