package org.ydownloader.downloader.youtube;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import com.github.axet.vget.VGet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ydownloader.downloader.DownloadHttps;
import org.ydownloader.downloader.DownloaderException;
import org.ydownloader.downloader.IDownloader;

import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.vhs.YouTubeMPGParser;
import com.github.axet.wget.info.DownloadInfo;


public class Youtube implements IDownloader {
	/**
	 * Hält die Informationen über das Video
	 */
	protected VideoInfo info;
	/**
	 * Speicherort
	 */
	protected File path;
	/**
	 * Video-Title
	 */
	protected String videoTitle;
	/**
	 * Fortschritt des Downloads
	 */
	protected int progress = 0;
	/**
	 * Downloads-HTTP Klasse
	 */
	protected DownloadHttps dl=null;
	/**
	 * Url welche heruntergeladen werden soll
	 */
	protected String DownloadURL;
	/*
	 * (non-Javadoc)
	 * @see org.ydownloader.downloader.IDownloader#run(java.lang.String, java.io.File)
	 */
	public void run(String url, File path) throws IOException, InterruptedException, Exception {
		/**
		 * Wenn es eine gültige Youtube-URL ist
		 */
		if (validYoutube(url)) {
				//
				URL web = new URL(url);
				//Parser holt die Infos vom Video
				VGetParser user = null;
				// Infors aus dem Netz laden
				user = VGet.parser(web);
				user = new YouTubeMPGParser();
				// VideoInfo extrahieren
				VideoInfo info = user.info(web);
				VGet v = new VGet(info, path);
				v.extract();
				//Videoinfos in der Instanz speichern
				this.videoTitle = info.getTitle();
				this.DownloadURL = info.getInfo().getSource().toString();
				// Download via HTTPS starten
				dl = new DownloadHttps();
				dl.download(info.getInfo().getSource(), path.getAbsolutePath());
				
		} else {
			throw new DownloaderException("Ungültiger Downloadlink");
		}
	}
	/**
	 * Konstruktor
	 */
	public Youtube() {

	}
	/*
	 * (non-Javadoc)
	 * @see org.ydownloader.downloader.IDownloader#getDownloadFinished()
	 */
	@Override
	public boolean getDownloadFinished() {
		return (this.progress > 100) ? true : false;

	}
	/*
	 * (non-Javadoc)
	 * @see org.ydownloader.downloader.IDownloader#getProgress()
	 */
	@Override
	public float getProgress() {
		if(dl==null) {
			return this.progress;
		} else {
			return dl.getProgress();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.ydownloader.downloader.IDownloader#getPath()
	 */
	@Override
	public File getPath() {
		return this.path;
	}
	/*
	 * (non-Javadoc)
	 * @see org.ydownloader.downloader.IDownloader#getUrl()
	 */
	@Override
	public String getUrl() {
		return this.DownloadURL;
	}
	/**
	 * Gibt den Titel des Videos zurück
	 * @return Titel
	 */
	public String getVideoTitle() {
		return this.videoTitle;
	}
	/**
	 * Prüft ob die URl ein Youtube-Link ist
	 * @param ytUrl Youtube-URL
	 * @return True wenn es ein gültiges Video ist
	 */
	public boolean validYoutube(String ytUrl) {
		String vId = null;
		//Prüfung mittels Pattern
		Pattern pattern = Pattern
				.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
		//Das Pattern auf den Input prüfen
		Matcher matcher = pattern.matcher(ytUrl);
		//prüfen ob das Pattern angewendet werden kann
		if (matcher.matches()) {
			vId = matcher.group(1);
		}
		// Wenn das Pattern nicht auf den String angwendet werden kann
		if (vId == null || vId.equals(new String(""))) {
			return false;
		} else {
			//Wenn das Pattern gefunden wurde
			return true;
		}
	}

}
