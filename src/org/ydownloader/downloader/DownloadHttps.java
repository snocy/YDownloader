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
/**
 * 
 * Diese Klasse ermöglicht den Download einer Datei über HTTPS
 */
public class DownloadHttps {
		/**
		 * Hält den aktuellen fortschritt des Downloads
		 */
	    private float progress=0.0f;
	    /**
	     * Leerer Konstruktor
	     */
	    public DownloadHttps() {}
	    /**
	     * Diese Funktion ermöglicht das Herunterladen einer Datei via 
	     * HTTPS.
	     * @param adress Download-Url
	     * @param path Speicherpfad
	     * @throws IOException Weitergabe von Exceptions
	     * @throws InterruptedException Weitergabe von Exceptions
	     * @throws Exception Weitergabe von Exceptions
	     */
	    public void download(URL adress, String path) throws IOException , InterruptedException , Exception{
	    	// Proudly stolen from http://stackoverflow.com/questions/10135074/download-file-from-https-server-using-java
	    	// Create a new trust manager that trust all certificates
	    	//Trust Manager der alle Certs akzepiert, egal ob selbst signiert oder nicht...
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
	    	//SSL starten
	    	    SSLContext sc = SSLContext.getInstance("SSL");
	    	    // SSL initialiserung
	    	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				URLConnection connection = adress.openConnection();
				// wie viel Bytes schon gelesen wurden
				int nBytesReceived = 0;
				//Wie groß die Datei ist
				int nTotalBytesInStream = connection.getContentLength();
				// InputStream zum Speichern des Download-Streams
				InputStream is = connection.getInputStream(); 
				//Speichern des Stream in einer Datei
				FileOutputStream out = new FileOutputStream(path);	
				//In 1024byte chunks schreiben
				byte[] buffer = new byte[1024];
				//gelsesene länge
				int len = is.read(buffer);
				//solange bytes da sind 
				while (len != -1) {
					//die bytes schrieben
				    out.write(buffer, 0, len);
				    //geschriebene Bytes lesen
				    len = is.read(buffer);
				    //zähler variable erhöhen
				    nBytesReceived += len;
				    // Fortschritt berechenen
				     progress = (float) ((float) nBytesReceived / (float) nTotalBytesInStream * 100);
				     //Wenn der Thread abbricht dann die Exception schmeissen 
				    if (Thread.interrupted()) {
				        throw new InterruptedException();
				    }
				}
				//alle pointer schließen (Wichtig!!)
				is.close();
				out.close();
	    }
	    /**
	     * Gibt den aktuellen fortschritt eines Downloads zurück
	     * @return Fortschritt
	     */
	    public float getProgress() {
	    	return this.progress;
	    }
	}

