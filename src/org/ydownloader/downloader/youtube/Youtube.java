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
	protected VideoInfo info;
	protected File path;
	protected long last;
	protected DownloadInfo dlInfo;
	protected String videoTitle;

	protected int progress = 0;
	protected DownloadHttps dl=null;
	protected String DownloadURL;

	public void run(String url, File path) throws IOException, InterruptedException, Exception {
		
		if (validYoutube(url)) {
				URL web = new URL(url);
				VGetParser user = null;
				user = VGet.parser(web);
				user = new YouTubeMPGParser();
				// create proper videoinfo to keep specific video information
				VideoInfo info = user.info(web);
				VGet v = new VGet(info, path);
				v.extract();
				
				this.videoTitle = info.getTitle();
				this.DownloadURL = info.getInfo().getSource().toString();
				dl = new DownloadHttps();
				dl.download(info.getInfo().getSource(), path.getAbsolutePath());
				//this.progress = (int)dl.getProgress();
		} else {
			throw new DownloaderException("Ungültiger Downloadlink");
		}
	}

	public Youtube() {

	}

	@Override
	public boolean getDownloadFinished() {
		return (this.progress > 100) ? true : false;

	}

	@Override
	public float getProgress() {
		if(dl==null) {
			return this.progress;
		} else {
			return dl.getProgress();
		}
	}

	@Override
	public File getPath() {
		return this.path;
	}

	@Override
	public String getUrl() {
		return this.DownloadURL;
	}

	public String getVideoTitle() {
		return this.videoTitle;
	}

	public boolean validYoutube(String ytUrl) {
		String vId = null;
		Pattern pattern = Pattern
				.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
		Matcher matcher = pattern.matcher(ytUrl);
		if (matcher.matches()) {
			vId = matcher.group(1);
		}
		if (vId == null || vId.equals(new String(""))) {
			return false;
		} else {
			return true;
		}
	}

}
