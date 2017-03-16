package unitech.au.com.unitechsolution;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by autoj on 2017-03-15.
 */
public class AsyncCallWS extends AsyncTask<Void, Void, Object> {
    private String wsUrl ="http://autoj.cafe24.com/WEBPOS/Unitech/UnitechAndroidServlet";
    private UserVo userVo;
    private JSONObject resJson = null;
    public AsyncCallWS()
    {
        this(null);
    }
    public AsyncCallWS(UserVo _vo)
    {
      this.userVo = _vo;
    }
    @Override
    protected Object doInBackground(Void... params) {
            if(userVo != null){
                try{
                JSONObject userJson = new JSONObject();
                userJson.put("ID",userVo.getID());
                userJson.put("PASS",userVo.getPASS());
                userJson.put("NAME",userVo.getNAME());
                userJson.put("ADDRESS",userVo.getADDRESS());
                userJson.put("DOB",userVo.getDOB());
                userJson.put("MOBILE",userVo.getMOBILE());
                userJson.put("EMOBILE",userVo.getEMOBILE());
                userJson.put("GENDER",userVo.getGENDER());
                postData(wsUrl, userJson);
               }catch (Exception e){}
            };
        return resJson;
    }

    public void postData(String url,JSONObject obj) {
        try {
                List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair("JSON", obj.toString()));
                Log.i("UNITECH", "User info =" + valuePairs.toString());
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(valuePairs));
                /*Parsing*/
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader br = null;
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                     br = new BufferedReader(new InputStreamReader(inputStream));
                     while ((line = br.readLine()) != null) {
                        sb.append(line);
                     }//while

                     String resFromServer = sb.toString();
                     if(resFromServer != null)
                     {
                        resJson = new JSONObject(resFromServer);
                     }//if
                } catch (Exception e){
                } finally {
                    if (br != null) {
                        try {
                              br.close();
                        } catch (IOException e) {}
                    }//if
                }//try


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
