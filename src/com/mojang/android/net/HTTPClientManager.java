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
  
  private HTTPClientManager() {
    BasicHttpParams params = new BasicHttpParams();
    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(params, "utf-8");
    ConnManagerParams.setTimeout(params, 30000L);
    params.setBooleanParameter("http.protocol.expect-continue", false);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    try
    {
      localSchemeRegistry.register(new Scheme("https", NoCertSSLSocketFactory.createDefault(), 443));
      this.mHTTPClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, localSchemeRegistry), params);
      return;
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
      Log.e("MCPE_ssl", "Couldn't create SSLSocketFactory");
    }
  }
  
  public static HttpClient getHTTPClient() {
    return instance.mHTTPClient;
  }
}

