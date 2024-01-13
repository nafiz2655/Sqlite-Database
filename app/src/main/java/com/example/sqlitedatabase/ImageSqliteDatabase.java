package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageSqliteDatabase extends AppCompatActivity {

    ListView list_view;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_sqlite_database);

        list_view = findViewById(R.id.list_view);
        ImageDatabase imageDatabase = new ImageDatabase(this);
        SQLiteDatabase sqLiteDatabase = imageDatabase.getWritableDatabase();



        Cursor cursor = imageDatabase.getData();

        /*
        if (cursor!=null){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String imge = cursor.getString(2);
                byte[] imageData = cursor.getBlob(3);

                Bitmap  bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            }
        }

         */

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] imageData = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                // Assuming you have an ImageView with id 'imageView' in your layout


               // String encodedImage = bitmapToBase64(bitmap);
               // String image = BitMapToString(bitmap);

                hashMap = new HashMap<>();
                hashMap.put("id", "" + id);
                hashMap.put("name", name);
                hashMap.put("image", bitmapToBase64(bitmap));
                arrayList.add(hashMap);

            }

        }

        MY_adapter myAdapter = new MY_adapter();
        list_view.setAdapter(myAdapter);

    }
    public class MY_adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View my_view = layoutInflater.inflate(R.layout.image,parent,false);
            HashMap<String,String> hashMap1 = arrayList.get(position);


            String sid = hashMap1.get("id");
            String sname = hashMap1.get("name");
            String simage = hashMap1.get("image");



            TextView textview =  my_view.findViewById(R.id.textview);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            ImageView image = my_view.findViewById(R.id.image);



            textview.setText(sid+"\n"+sname);
            //image.setImageResource(R.drawable.image);


            Bitmap decodedImage = base64ToBitmap(simage);


            image.setImageBitmap(decodedImage);



            return my_view;
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