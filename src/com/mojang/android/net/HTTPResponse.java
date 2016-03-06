package com.mojang.android.net;

import org.apache.http.Header;

public class HTTPResponse
{
  public static final int ABORTED = 2;
  public static final int DONE = 1;
  public static final int IN_PROGRESS = 0;
  public static final int TIME_OUT = 3;
  String body = "";
  Header[] headers;
  int responseCode = -100;
  int status = 0;
  
  public String getBody()
  {
    return this.body;
  }
  
  public Header[] getHeaders()
  {
    return this.headers;
  }
  
  public int getResponseCode()
  {
    return this.responseCode;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setBody(String paramString)
  {
    this.body = paramString;
  }
  
  public void setHeaders(Header[] paramArrayOfHeader)
  {
    this.headers = paramArrayOfHeader;
  }
  
  public void setResponseCode(int paramInt)
  {
    this.responseCode = paramInt;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}


/* Location:              /home/aurelien/C/mcpemod/decompile/classes-dex2jar.jar!/com/mojang/android/net/HTTPResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */