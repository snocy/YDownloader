package org.ydownloader.downloader;

import java.io.File;

public interface IDownloader {
	public boolean getDownloadFinished();
	public int getProgress();
	public File getPath();
	public String getUrl();
	public void run(String url, File path) throws DownloaderException;
}
