package com.example.lab2;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	TextView textView;
	ArrayList<String>arr1 = new ArrayList<String>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	AsyncHttpPost asyncHttpPost = new AsyncHttpPost();       
	asyncHttpPost.execute("http://domain--/~800361/pricesData.php");
        
    }

    public class AsyncHttpPost extends AsyncTask<String, String, ArrayList<String>> {
    	public AsyncHttpPost(){
    	}
    	    @Override
    	    protected ArrayList<String> doInBackground(String... params) {
    	     byte[] result = null;
    	      String str = "";
    	      HttpClient client = new DefaultHttpClient();
    	      HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
    	      try {
    	      HttpResponse response = client.execute(post);
    	      StatusLine statusLine = response.getStatusLine();
    	      if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
    	      result = EntityUtils.toByteArray(response.getEntity());
    	      str = new String(result, "UTF-8");
    	         }
    	      JSONArray all = new JSONArray(str);
    	      for (int i=0; i<all.length(); i++){
    	     // JSONObject item=all.getJSONObject(i);
    	      String owner = all.getJSONObject(i).getString("OWNER");
    	      String number = all.getJSONObject(i).getString("NUMBER");
    	      arr1.add(owner + " - "+number);
    	     }
    	      }catch (JSONException e) {
    	    	  e.printStackTrace();
    	      }
    	      catch (UnsupportedEncodingException e) {
    	       e.printStackTrace();
    	     }
    	    catch (Exception e) {
    	    }
    	   return arr1;
    	  }
    	@Override
    	protected void onPostExecute(ArrayList<String> output) {
    		LinearLayout line = (LinearLayout)findViewById(R.id.text);
    		for(int j = 0;j<output.size();j++)
    		{
    			TextView me = new TextView(MainActivity.this);
    			me.setText(output.get(j));
    			line.addView(me);
    		}
    	}
    	}   
    
    
    
    
}
