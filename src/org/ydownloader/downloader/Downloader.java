package org.ydownloader.downloader;

import java.io.File;

public interface Downloader {
	public boolean DownloadFinished();
	public int getProgress();
	public File getPath();
	public String getUrl();
	public void run(String url, File path);
}
