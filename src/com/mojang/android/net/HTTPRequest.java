package com.mojang.android.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HTTPRequest
{
  String mCookieData = "";
  HttpRequestBase mHTTPRequest = null;
  String mRequestBody = "";
  String mRequestContentType = "text/plain";
  HTTPResponse mResponse = new HTTPResponse();
  String mURL = "";
  
  private void addBodyToRequest(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase)
  {
    if (this.mRequestBody != "") {}
    try
    {
      StringEntity localStringEntity = new StringEntity(this.mRequestBody);
      localStringEntity.setContentType(this.mRequestContentType);
      paramHttpEntityEnclosingRequestBase.setEntity(localStringEntity);
      paramHttpEntityEnclosingRequestBase.addHeader("Content-Type", this.mRequestContentType);
      return;
    }
    catch (UnsupportedEncodingException exception)
    {
      exception.printStackTrace();
    }
  }
  
  private void addHeaders()
  {
    this.mHTTPRequest.addHeader("User-Agent", "MCPE/Android");
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 3000);
    this.mHTTPRequest.setParams(localBasicHttpParams);
    if ((this.mCookieData != null) && (this.mCookieData.length() > 0)) {
      this.mHTTPRequest.addHeader("Cookie", this.mCookieData);
    }
    this.mHTTPRequest.addHeader("Charset", "utf-8");
  }
  
  private void createHTTPRequest(String request) {
        if (request.equals("DELETE"))
        {
          this.mHTTPRequest = new HttpDelete(this.mURL);
          return;
        }
        if (request.equals("PUT"))
        {
          HttpPut put = new HttpPut(this.mURL);
          addBodyToRequest(put);
          this.mHTTPRequest = put;
          return;
        }
        if (request.equals("GET")) {
          this.mHTTPRequest = new HttpGet(this.mURL);
          return;
        }
        if (request.equals("POST")) {
          HttpPost post = new HttpPost(this.mURL);
          addBodyToRequest(post);
          this.mHTTPRequest = post;
          return;
        }
      
    throw new InvalidParameterException("Unknown request method " + request);
  }
  
  public void abort()
  {
    try
    {
      this.mResponse.setStatus(2);
      if (this.mHTTPRequest != null) {
        this.mHTTPRequest.abort();
      }
      return;
    }
    finally
    {
	Log.e("GenericLauncher","HTTPRequest.abort");
      //localObject = ;
      //throw ((Throwable)localObject);
    }
  }
  
  public HTTPResponse send(String paramString) {
    createHTTPRequest(paramString);
    addHeaders();
    if (this.mResponse.getStatus() == 2) {
      return this.mResponse;
    }
    try
    {
      HttpResponse response = HTTPClientManager.getHTTPClient().execute(this.mHTTPRequest);
      mResponse.setResponseCode(response.getStatusLine().getStatusCode());
      HttpEntity localHttpEntity = response.getEntity();
      this.mResponse.setBody(EntityUtils.toString(localHttpEntity));
      this.mResponse.setStatus(1);
      this.mResponse.setHeaders(response.getAllHeaders());
      return this.mResponse;
    }
    catch (ConnectTimeoutException connectTimeoutException)
    {
      this.mResponse.setStatus(3);
      this.mHTTPRequest = null;
      return this.mResponse;
    }
    catch (ClientProtocolException clientProtocolException)
    {
      clientProtocolException.printStackTrace();
    }
    catch (IOException ioException)
    {
      ioException.printStackTrace();
    }
    return null;
  }
  
  public void setContentType(String contentType) {
    this.mRequestContentType = contentType;
  }
  
  public void setCookieData(String cookieData) {
    this.mCookieData = cookieData;
  }
  
  public void setRequestBody(String requestBody) {
    this.mRequestBody = requestBody;
  }
  
  public void setURL(String url) {
    this.mURL = url;
  }
}

