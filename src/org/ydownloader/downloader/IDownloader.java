package org.ydownloader.downloader;

import java.io.File;
import java.io.IOException;

public interface IDownloader {
	/**
	 * Gibt zurück ob ein Download fertig ist
	 * @return Ob der Download fertig ist.
	 */
	public boolean getDownloadFinished();
	/**
	 * Gibt den aktuellen Fortschritt des Downloads zurück
	 * @return Prozent
	 */
	public float getProgress();
	/**
	 * Gibt den aktuellen Speicherort zurück
	 * @return Speicherort
	 */
	public File getPath();
	/**
	 * Gibt die aktuelle Download-URL zurück
	 * @return URL
	 */
	public String getUrl();
	/**
	 * Führt einen Download durch.
	 * @param url Download-URL
	 * @param path Speicherpfad
	 * @throws DownloaderException Fehlermeldungen weitereichen
	 * @throws IOException Fehlermeldungen weitereichen
	 * @throws InterruptedException Fehlermeldungen weitereichen
	 * @throws Exception Fehlermeldungen weitereichen
	 */
	public void run(String url, File path) throws DownloaderException, IOException, InterruptedException, Exception;
}
