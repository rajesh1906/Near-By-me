package com.chrajeshkumar.nearby.Utils;

import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeoutException;

public class UrltoValue
{
	static String page="zero";
	public static String getValuefromUrl(String url) throws TimeoutException
	{
		try
		{
		  final int TIMEOUT_MILLISEC = 50000;
          HttpParams httpParams = new BasicHttpParams();
          HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
          HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
          HttpConnectionParams.setTcpNoDelay(httpParams, true);
          HttpClient httpClient = new DefaultHttpClient(httpParams);
			//DefaultHttpClient httpClient = new DefaultHttpClient();
            URLEncoder.encode(url, "UTF-8");
			HttpGet httpPost = new HttpGet(url);
			
		    ResponseHandler<String> resHandler = new BasicResponseHandler();
			 page = httpClient.execute(httpPost, resHandler);
			Log.v("PAGE",page);
			return page;
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String zero="zero";
			return zero;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String zero="zero";
			return zero;
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			String zero="zero";
			return zero;
			 //Toast.makeText(context, "Please remove % symbol", Toast.LENGTH_SHORT);
		}
		catch(Exception e)

		{
			e.printStackTrace();
			String zero="zero";
			return zero;
			
		}
	}
}
