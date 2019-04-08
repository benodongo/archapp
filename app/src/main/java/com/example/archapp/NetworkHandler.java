package com.example.archapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHandler {
    public static String get(String path){
        String result=null;
        HttpURLConnection httpURLConnection=null;
        try {
            URL url=new URL(path);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream in=new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer buffer=new StringBuffer();
            while ((line=bufferedReader.readLine())!= null){
                buffer.append(line);
                buffer.append('\r');
            }
            bufferedReader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
    }
    /*
     * makes a post request and returns the result as JSON string
     * Authorization is enabled just change the credentials
     * @param targetURL the url you are calling
     * @param urlParameters the parameters that a encoded with URLEncoder.encode()
     * */
    public static String post(String targetURL, String urlParameters)
    {
        URL url;
        HttpURLConnection connection = null;
        //System.setProperty("http.keepAlive", "false");
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();
            //Get Response
            InputStream is ;
            if(connection.getResponseCode()/100 ==2){
                is=connection.getInputStream();
            }else{
                is=connection.getErrorStream();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}

