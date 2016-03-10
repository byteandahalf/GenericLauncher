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
  
  public String getBody() {
    return this.body;
  }
  
  public Header[] getHeaders() {
    return this.headers;
  }
  
  public int getResponseCode() {
    return this.responseCode;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setBody(String body) {
    this.body = body;
  }
  
  public void setHeaders(Header[] headers) {
    this.headers = headers;
  }
  
  public void setResponseCode(int code) {
    this.responseCode = code;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
}

