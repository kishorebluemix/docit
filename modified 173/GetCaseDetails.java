package com.mediassure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mediassure.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GetCaseDetails extends Activity {
	
	
	TextView tv1 = null;
	TextView tv2 = null;
	TextView tv3 = null;
	TextView tv4 = null;
	TextView tv5 = null;
	Button aaa;
	ImageView i1 = null;
	ImageView i2 = null;
	ImageView i3 =null;

	boolean isImageFitToScreen = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_case_details);
		tv1 = (TextView)findViewById(R.id.textView1);
		tv2 = (TextView)findViewById(R.id.textView2);
		tv3 = (TextView)findViewById(R.id.textView3);
		tv4 = (TextView)findViewById(R.id.textView4);
		tv5 = (TextView)findViewById(R.id.textView5);
		i1 = (ImageView)findViewById(R.id.imageView1);
		i2 = (ImageView)findViewById(R.id.imageView2);
		i3 = (ImageView)findViewById(R.id.imageView3);
		aaa= (Button) findViewById(R.id.aaa);
		JSONParser jsonParser = new JSONParser();
    	JSONArray array = null;
    	Bundle b=getIntent().getExtras();
    	String url = "http://docit.tcs.us-south.mybluemix.net/api/case?caseId="+b.getString("caseid");
    	try {
    	    // Getting JSON Object
    	    array = jsonParser.getJSONFromUrl(url);
    	}catch (Exception e) {
			System.out.println("Exception in json retrieving");
			e.printStackTrace();
		}
    	JSONObject object = null;
    	    try {
    	    	
				object = array.getJSONObject(0);
				String docComments = object.getString("docComments");
				String doctorComments = (docComments == null || "null".equals(docComments)) ? "" :docComments;
				tv1.setText(object.getString("caseName"));
				tv2.setText("Status : "+object.getString("caseStatus"));
				tv3.setText("Doctor Comments: "+doctorComments);
				tv4.setText("Description : "+object.getString("caseDesc"));
				Long l = Long.parseLong(object.getString("caseCreationTS"));
				Timestamp t1=new Timestamp(l);
				Date date = new Date(t1.getTime());
				
				tv5.setText("Created on : "+date);
				
				
				int i = Integer.parseInt(b.getString("caseid"));
				String url1="http://docit.tcs.us-south.mybluemix.net/api/case/image?caseFileId="+(i+1);
				String url2="http://docit.tcs.us-south.mybluemix.net/api/case/image?caseFileId="+(i+2);
				String url3="http://docit.tcs.us-south.mybluemix.net/api/case/image?caseFileId="+(i+3);
				System.out.println("url1 : "+url1);
				System.out.println("url2 : "+url2);
				System.out.println("url3 : "+url3);
				getImg2 g1=new getImg2();
				getImg2 g2=new getImg2();
				getImg2 g3=new getImg2();
				File epath = Environment.getExternalStoragePublicDirectory(
		                 Environment.DIRECTORY_PICTURES);
				Bitmap b1=null,b2=null,b3=null;
		           File file1 = new File(epath, ""+(i+1)+".png");
		           if(file1.exists())
		           {
		        	  String filePath = file1.getPath();  
		        			  b1 = BitmapFactory.decodeFile(filePath); 
		           }
		           else
		           {
		        	   b1=g1.execute(url1, ""+(i+1)).get();
		           }
		           file1 = new File(epath, ""+(i+2)+".png");
		           if(file1.exists())
		           {
		        	  String filePath = file1.getPath();  
		        			  b2 = BitmapFactory.decodeFile(filePath); 
		           }
		           else
		           {
		        	   b2=g2.execute(url1, ""+(i+2)).get();
		           }
		           file1 = new File(epath, ""+(i+3)+".png");
		           if(file1.exists())
		           {
		        	  String filePath = file1.getPath();  
		        			  b3 = BitmapFactory.decodeFile(filePath); 
		           }
		           else
		           {
		        	   b3=g3.execute(url1, ""+(i+3)).get();
		           }
				
				i1.setImageBitmap(b1);
				i2.setImageBitmap(b2);
				i3.setImageBitmap(b3);
    	    } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);

    	    i1.setOnClickListener(new OnClickListener() {
    	        @SuppressLint("NewApi")
				@Override
    	        public void onClick(View v) {
    	            // TODO Auto-generated method stub
    	        	Bundle bundle= new Bundle();
    	        	BitmapDrawable drawable = (BitmapDrawable) i1.getDrawable();
    		        Bitmap bitmap = drawable.getBitmap();          
    		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
    		        byte [] byte_arr = stream.toByteArray();
    		        String image_str1 = Base64.encodeToString(byte_arr, Base64.DEFAULT);
    	            bundle.putString("img", image_str1);
    	            Intent i = new Intent(getApplicationContext(),CaseImage.class);
    	            i.putExtras(bundle);
    	            startActivity(i);

    	        }
    	    });
    	    i2.setOnClickListener(new OnClickListener() {
    	        @SuppressLint("NewApi")
				@Override
    	        public void onClick(View v) {
    	            // TODO Auto-generated method stub

    	        	Bundle bundle= new Bundle();
    	        	BitmapDrawable drawable = (BitmapDrawable) i2.getDrawable();
    		        Bitmap bitmap = drawable.getBitmap();          
    		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
    		        byte [] byte_arr = stream.toByteArray();
    		        String image_str1 = Base64.encodeToString(byte_arr, Base64.DEFAULT);
    	            bundle.putString("img", image_str1);
    	            Intent i = new Intent(getApplicationContext(),CaseImage.class);
    	            i.putExtras(bundle);
    	            startActivity(i);

    	        }
    	    });
    	    i3.setOnClickListener(new OnClickListener() {
    	        @SuppressLint("NewApi")
				@Override
    	        public void onClick(View v) {
    	            // TODO Auto-generated method stub

    	        	Bundle bundle= new Bundle();
    	        	BitmapDrawable drawable = (BitmapDrawable) i3.getDrawable();
    		        Bitmap bitmap = drawable.getBitmap();          
    		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
    		        byte [] byte_arr = stream.toByteArray();
    		        String image_str1 = Base64.encodeToString(byte_arr, Base64.DEFAULT);
    	            bundle.putString("img", image_str1);
    	            Intent i = new Intent(getApplicationContext(),CaseImage.class);
    	            i.putExtras(bundle);
    	            startActivity(i);

    	        }
    	    });
    	    aaa.setOnClickListener(new OnClickListener() {
				@Override
    	        public void onClick(View v) {
    	            // TODO Auto-generated method stub

    	        	
    	            Intent i = new Intent(getApplicationContext(),BookAppointment.class);
    	            startActivity(i);

    	        }
    	    });	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_case_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
}
class getImg2  extends AsyncTask<String, Void, Bitmap>
{
	@Override
	protected Bitmap doInBackground(String... params) {
		System.out.println("Inside do background");

	    try {
	    	System.out.println("Inside getBitmap function");
	        Log.e("src",params[0]);
	        URL url = new URL(params[0]);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	        byte[] byteArray = stream.toByteArray();
	        File path = Environment.getExternalStoragePublicDirectory(
	                  Environment.DIRECTORY_PICTURES);
	           path.mkdirs();

	           File file1 = new File(path, params[1]+".png");

				FileOutputStream s1 = new FileOutputStream(file1);
				s1.write(byteArray);
				s1.close();

	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("Exception",e.getMessage());
	        return null;
	    }
	}
}