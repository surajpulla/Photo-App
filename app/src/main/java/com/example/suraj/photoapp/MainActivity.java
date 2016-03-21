package com.example.suraj.photoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView list;
    String titlevalue="";
    String value;
MyDBHandler dbHandle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent CameraActivityIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(CameraActivityIntent);

            }
        });




        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
             value = extras1.getString("sentdata");
             titlevalue=extras1.getString("edittextstring");}
        else{



            dbHandle=new MyDBHandler(this,null,null,1);

            value = dbHandle.databsetoString();
            titlevalue = dbHandle.databsetoString1();
            if(value == "" || titlevalue == "" || value == null || titlevalue == null ){
                value="";
                titlevalue="";
                Toast.makeText(getApplicationContext(), "Please take a photo to add notes", Toast.LENGTH_LONG).show();

            }
        }
         //  TextView list=(TextView) findViewById(R.id.textView2);
         //   list.setText(value);


            //creating a list view
            String food[]={"tuna","bacon","pork","chicken","potato","ham"};
            // String value1 = extras1.getString("sentdata");
           final  String[] ary = value.split("\n");
            final String[] titleary=titlevalue.split("\n");

//
//

            ListAdapter myAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleary);
            ListView myListView= (ListView) findViewById(R.id.listView);
            myListView.setAdapter(myAdapter);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            String foodname = String.valueOf(parent.getItemAtPosition(position));

                            int index = -1;
                            for (int i=0;i<titleary.length;i++) {
                                if (titleary[i].equals(foodname)) {
                                    index = i;
                                    break;
                                }
                            }


                           // Intent sentdatatophotodetail = new Intent(getApplicationContext(),MainActivity.class);
                           // sentdatatophotodetail.putExtra("sentdatatophotodetail",foodname );

                            Toast.makeText(getApplicationContext(),ary[index] , Toast.LENGTH_LONG).show();
                            Intent  photoDetailIntent= new Intent(getApplicationContext(), PhotoDetail.class);
                           //  photoDetailIntent.putExtra("sentdatatophotodetail",foodname );
                            photoDetailIntent.putExtra("sentdatatophotodetail",ary[index] );
                            photoDetailIntent.putExtra("titlesentdatatophotodetail",foodname );
                            startActivity(photoDetailIntent);
                        }
                    }
            );





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
}
