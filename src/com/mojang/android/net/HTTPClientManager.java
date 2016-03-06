package com.mojang.android.net;

import android.util.Log;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HTTPClientManager
{
  static HTTPClientManager instance = new HTTPClientManager();
  HttpClient mHTTPClient = null;
  String mHttpClient;
  
  private HTTPClientManager()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(localBasicHttpParams, "utf-8");
    ConnManagerParams.setTimeout(localBasicHttpParams, 30000L);
    localBasicHttpParams.setBooleanParameter("http.protocol.expect-continue", false);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    try
    {
      localSchemeRegistry.register(new Scheme("https", NoCertSSLSocketFactory.createDefault(), 443));
      this.mHTTPClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("MCPE_ssl", "Couldn't create SSLSocketFactory");
      }
    }
  }
  
  public static HttpClient getHTTPClient()
  {
    return instance.mHTTPClient;
  }
}


/* Location:              /home/aurelien/C/mcpemod/decompile/classes-dex2jar.jar!/com/mojang/android/net/HTTPClientManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */