package edu.quinnipiac.ls03_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST = 200;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //implicit intent handling to display an image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String result = data.getStringExtra("returnKey");
            if (result != null && result.length() > 0)
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            InputStream stream = null;
            try {

                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                if (imageView == null)
                    Log.e(LOG_TAG,"imageView null");
                else
                    imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.result);
    }

    public void onClick(View view) {
        EditText text = (EditText) findViewById(R.id.inputforintent);
        String value = text.getText().toString();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("inputKey", value);

        startActivityForResult(intent, REQUEST_CODE);
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}