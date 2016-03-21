package com.example.suraj.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoDetail extends AppCompatActivity {

    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
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

    this.setPic();
    }



    public void setPic() {
        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            String value = extras2.getString("sentdatatophotodetail");
            String titlereceivedvalue = extras2.getString("titlesentdatatophotodetail");
textView3=(TextView)findViewById(R.id.textView3);
            textView3.setText(titlereceivedvalue);
//String value="/storage/emulated/0/Pictures/JPEG_20160207_203401_-874797238.jpg";
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;  // Experiment with different sizes
            //Bitmap b = BitmapFactory.decodeFile(filePath, options);

            Bitmap bmImg = BitmapFactory.decodeFile(value,options);
            Log.v("surajpulla",value);
            ImageView imgview = (ImageView) findViewById(R.id.imageView);
            imgview.setImageBitmap(bmImg);
         //   imgview.setImageResource(R.mipmap.ic_launcher);


        }
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

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_SHORT).show();
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

}
