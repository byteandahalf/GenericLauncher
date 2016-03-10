package com.mojang.android.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class NoCertSSLSocketFactory
  extends SSLSocketFactory {

  private SSLContext sslContext = SSLContext.getInstance("TLS");
  
  public NoCertSSLSocketFactory(KeyStore paramKeyStore)
    throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
    super(paramKeyStore);
    X509TrustManager trustManager = new X509TrustManager()
    {
      public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {}
      
      public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {}
      
      public X509Certificate[] getAcceptedIssuers()
      {
        return null;
      }
    };
    this.sslContext.init(null, new TrustManager[] { trustManager }, null);
  }
  
  public static NoCertSSLSocketFactory createDefault()
    throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(null, null);
    NoCertSSLSocketFactory factory  = new NoCertSSLSocketFactory(keystore);
    factory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    return factory;
  }
  
  public Socket createSocket()
    throws IOException {
    return this.sslContext.getSocketFactory().createSocket();
  }
  
  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
    throws IOException, UnknownHostException {
    return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
  }
}

