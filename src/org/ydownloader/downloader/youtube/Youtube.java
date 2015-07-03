package org.ydownloader.downloader.youtube;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.axet.vget.VGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ydownloader.downloader.DownloaderException;
import org.ydownloader.downloader.IDownloader;

import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.vhs.VimeoInfo;
import com.github.axet.vget.vhs.YouTubeMPGParser;
import com.github.axet.vget.vhs.YoutubeInfo;
import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadError;

public class Youtube implements IDownloader {
		protected VideoInfo info;
	    protected File path;
	    //protected String url;
	    protected long last;
	    protected DownloadInfo dlInfo;
	    protected String videoTitle;
	    protected boolean downloadFinished=false;
	    protected int progress=0;
	    protected AtomicBoolean stop;

	        public void run(String url, File path) {
	            try {
	                AtomicBoolean stop = new AtomicBoolean(false);
	                Runnable notify = new Runnable() {
	                    @Override
	                    public void run() {
	                        VideoInfo i1 = info;
	                        DownloadInfo i2 = i1.getInfo();

	                        // notify app or save download state
	                        // you can extract information from DownloadInfo info;
	                        switch (i1.getState()) {
	                        case EXTRACTING:
	                        case EXTRACTING_DONE:
	                        case DONE:
	                            if (i1 instanceof YoutubeInfo) {
	                                YoutubeInfo i = (YoutubeInfo) i1;
	                                System.out.println(i1.getState() + " " + i.getVideoQuality());
	                            } else if (i1 instanceof VimeoInfo) {
	                                VimeoInfo i = (VimeoInfo) i1;
	                                System.out.println(i1.getState() + " " + i.getVideoQuality());
	                            } else {
	                                System.out.println("downloading unknown quality");
	                            }
	                            break;
	                        case RETRYING:
	                            System.out.println(i1.getState() + " " + i1.getDelay());
	                            break;
	                        case DOWNLOADING:
	                            long now = System.currentTimeMillis();
	                            if (now - 1000 > last) {
	                                last = now;

	                                String parts = "";

	                                List<Part> pp = i2.getParts();
	                                if (pp != null) {
	                                    // multipart download
	                                    for (Part p : pp) {
	                                        if (p.getState().equals(States.DOWNLOADING)) {
	                                            parts += String.format("Part#%d(%.2f) ", p.getNumber(), p.getCount()
	                                                    / (float) p.getLength());
	                                        }
	                                    }
	                                }

	                                System.out.println(String.format("%s %.2f %s", i1.getState(),
	                                        i2.getCount() / (float) i2.getLength(), parts));
	                            }
	                            break;
	                        default:
	                            break;
	                        }
	                    }
	                };

	                URL web = new URL(url);

	                // [OPTIONAL] limit maximum quality, or do not call this function if
	                // you wish maximum quality available.
	                //
	                // if youtube does not have video with requested quality, program
	                // will raise en exception.
	                VGetParser user = null;

	                // create proper html parser depends on url
	                user = VGet.parser(web);

	                // download maximum video quality from youtube
	                // user = new YouTubeQParser(YoutubeQuality.p480);

	                // download mp4 format only, fail if non exist
	                // user = new YouTubeMPGParser();

	                // create proper videoinfo to keep specific video information
	                info = user.info(web);

	                VGet v = new VGet(info, path);

	                // [OPTIONAL] call v.extract() only if you d like to get video title
	                // or download url link
	                // before start download. or just skip it.
	                v.extract(user, stop, notify);

	                System.out.println("Title: " + info.getTitle());
	                System.out.println("Download URL: " + info.getInfo().getSource());

	                v.download(user, stop, notify);
	            } catch (RuntimeException e) {
	                throw e;
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        }

	   
	public Youtube() {
		       
		    }
	@Override
	public boolean getDownloadFinished() {
		return downloadFinished;
		
	}
	@Override
	public int getProgress() {
		
		return 0;
	}
	@Override
	public File getPath() {
		
		return this.path;
	}
	@Override
	public String getUrl() {
		
		return "aaa";
	}
	public String getVideoTitle()  {
		return this.videoTitle;
	}
	/**
	 * proudly stolen from http://stackoverflow.com/questions/13537202/play-high-quality-youtube-videos-using-videoview#comment19679509_13537202
	 * @param result
	 * @return
	 */

	}

