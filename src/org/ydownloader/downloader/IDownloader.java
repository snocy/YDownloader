package org.ydownloader.downloader;

import java.io.File;
import java.io.IOException;

public interface IDownloader {
	public boolean getDownloadFinished();
	public float getProgress();
	public File getPath();
	public String getUrl();
	public void run(String url, File path) throws DownloaderException, IOException, InterruptedException, Exception;
}
