package org.ydownloader.downloader;
/**
 * 
 * Diese Klasse ist für die Exceptions während des Download-Vorganges verantwortlich.
 *
 */

public class DownloaderException extends Exception {
	public DownloaderException()  { }
	  public DownloaderException(String s)
	  {	    super(s);	
	  }
}
