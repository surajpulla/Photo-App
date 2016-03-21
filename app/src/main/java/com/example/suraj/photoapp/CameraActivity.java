package com.example.suraj.photoapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.content.pm.PackageInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE=1;
    ImageView myImageView;
    String currentPhotoPath="";
    File photoFile;
    MyDBHandler dbHandler;
    TextView buckysText;
    Intent intent;
    EditText editText;
    String currenttitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button myButton=(Button) findViewById(R.id.myButton);
        myImageView=(ImageView) findViewById(R.id.myImageView);


      //  buckysInput=(EditText) findViewById(R.id.editText);
      //  buckysText=(TextView)findViewById(R.id.textView);
        dbHandler=new MyDBHandler(this,null,null,1);

      //  printDatabase();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Settings selected",Toast.LENGTH_SHORT ).show();
            return true;
        }

        if (id == R.id.action_uninstall) {
            Uri packageURI = Uri.parse("package:com.example.suraj.photoapp");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);

            Toast.makeText(getApplicationContext(),"Uninstall",Toast.LENGTH_SHORT ).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchCamera(View view){
         intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
             photoFile = null;
            try {
                photoFile = createImageFile();
           } catch (IOException ex) {
                // Error occurred while creating the File
               Log.v("hello","file not created");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                //take a picture and paass results along to activity results
            }
        }

    }

    //if you want to return image
    //called after startactivityresult


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                //request is for taking and imaage and result is if no errors we there during capture
                //get the photo


                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                myImageView.setImageBitmap(photo);
                // Toast.makeText(getApplicationContext(),currentPhotoPath,Toast.LENGTH_LONG);

            }
        }
        catch (Exception e)
        {
            Log.v("surajexception",e.toString());
        }

    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath =image.getAbsolutePath();
        Log.v("hellooo",":"+ currentPhotoPath);
        return image;
    }


    public void addButtonClicked(View view){
        editText=(EditText)findViewById(R.id.editText);
        currenttitle=editText.getText().toString();
        Products product = new Products(currentPhotoPath,currenttitle);
        dbHandler.addProducts(product);

        printDatabase();




    }
    public void deleteButtonClicked(View view){
//        String inputText=buckysInput.getText().toString();
//        dbHandler.deleteProducts(inputText);
//        printDatabase();
        dbHandler.deleteTable();
        printDatabase();
   }

    public void printDatabase(){
        String dbString=dbHandler.databsetoString();
        Intent sentdata = new Intent(getApplicationContext(),MainActivity.class);
        sentdata.putExtra("sentdata",dbString );

        sentdata.putExtra("edittextstring", dbHandler.databsetoString1() );

        startActivity(sentdata);
       // buckysText.setText(dbString);
      //  buckysInput.setText("");
    }



}
