package DEPRICATED;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.group7mealerapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Base64;


/**
 * sample code taken from stack overflow link below
 * https://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
 * altered code to take an image and save it in base64 encoding format that is then saved in FB
 */
public class Camera extends Activity
{
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageView = (ImageView)this.findViewById(R.id.imageViewCamera);
        Button photoButton = (Button) this.findViewById(R.id.btnOpenCamera);
        //when you click the photobutton want to open up the camera and take a pic then save
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //ask for permission to use camera first this is done automatically through android
                //where it asks the user for perms
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    /**
     * method that verifies the correct permissions and handles the cases
     * @param requestCode if request code is 100 (camera permission code) allows access else denies
     * @param permissions
     * @param grantResults when results are permission granted then uses camera
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * gets the results of the camera picture being taken, saves it into a bitmap that is then
     * encoded in base64 and saved in the database
     * @param requestCode
     * @param resultCode
     * @param data bitmap that is encoded
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = databaseReference = firebaseDatabase.getReference("UserInfo");
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            //convert bitmap saved into a base 64 to be stored in Firebase
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encodedimg = Base64.getEncoder().encodeToString(byteArray);

        }
    }
}