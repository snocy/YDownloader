package org.ydownloader.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadMultipartError;
public class DownloadHttps {
	    AtomicBoolean stop = new AtomicBoolean(false);
	    DownloadInfo info;
	    long last;
	    private float progress=0.0f;
	    public DownloadHttps() {}
	    public void download(URL adress, String path) throws IOException , InterruptedException , Exception{
	    	// Proudly stolen from http://stackoverflow.com/questions/10135074/download-file-from-https-server-using-java
	    	// btw. thank you for that :)
	    	// Create a new trust manager that trust all certificates
	    	TrustManager[] trustAllCerts = new TrustManager[]{
	    	    new X509TrustManager() {
	    	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	    	            return null;
	    	        }
	    	        public void checkClientTrusted(
	    	            java.security.cert.X509Certificate[] certs, String authType) {
	    	        }
	    	        public void checkServerTrusted(
	    	            java.security.cert.X509Certificate[] certs, String authType) {
	    	        }
	    	    }
	    	    
	    	};
	    	    SSLContext sc = SSLContext.getInstance("SSL");
	    	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				URLConnection connection = adress.openConnection();
				int nBytesReceived = 0;
				int nTotalBytesInStream = connection.getContentLength();
				InputStream is = connection.getInputStream();    	
				FileOutputStream out = new FileOutputStream(path);		
				byte[] buffer = new byte[1024];
				int len = is.read(buffer);
				while (len != -1) {
				    out.write(buffer, 0, len);
				    len = is.read(buffer);
				    nBytesReceived += len;
				     progress = (float) ((float) nBytesReceived / (float) nTotalBytesInStream * 100);
				    if (Thread.interrupted()) {
				        throw new InterruptedException();
				    }
				}
				is.close();
				out.close();
	    }
	    public float getProgress() {
	    	return this.progress;
	    }
	}

