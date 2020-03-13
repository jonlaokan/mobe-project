package mobe.m2dl.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> list = (List<String>) getIntent().getSerializableExtra("list");
        Toast.makeText(ImagesActivity.this, "Veuillez Patientez quelques secondes!", Toast.LENGTH_SHORT).show();

        ImagesActivity.this.setContentView(R.layout.images_layout);
        list = (list!=null) ? list : new ArrayList<String>();
        LinearLayout v = findViewById(R.id.photos_container);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        for (String s : list){
            StorageReference reference = storage.getReference(s);
            final ImageView imageView = new ImageView(this);
            v.addView(imageView);
            reference.getBytes(1024*1024*1000).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    byte [] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }
}
