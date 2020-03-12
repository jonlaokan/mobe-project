package mobe.m2dl.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.ExifInterface;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class FireUtils {


    void writeImage(Bitmap bitmap, Location location, final Runnable onSuccess, final Runnable onFailure) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference r = storage.getReference("image/im_" + new Date().getTime()+ ".png");
            StorageMetadata storageMetadata = new StorageMetadata.Builder()
                    .setCustomMetadata("lat",Double.toString(location.getLatitude()))
                    .setCustomMetadata("long",Double.toString(location.getLongitude()))
                    .build();

            UploadTask uploadTask = r.putBytes(data,storageMetadata);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception exception) {
                    onFailure.run();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    onSuccess.run();
                }
            });





    }
;
}
