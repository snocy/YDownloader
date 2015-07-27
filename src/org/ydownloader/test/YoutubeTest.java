package org.ydownloader.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ydownloader.downloader.DownloadHttps;
import org.ydownloader.downloader.DownloaderException;
import org.ydownloader.downloader.youtube.Youtube;

import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.vhs.YouTubeMPGParser;

public class YoutubeTest {
	public static void main(String[] args) {
		new YoutubeTest();
	}
	public YoutubeTest() {
		String url = "https://www.youtube.com/watch?v=fjRcYm3g90E";
		
		 if(validYoutube(url)) {
			 try {
				 String path = "/home/benedict/Downloads/youtube/videos/UZHUNKHRSXs.mp4";
				 System.out.println("gültiger link!");
		     	//VGet v;
			
				URL web = new URL(url);
				//v = new VGet(web, new File(path));
				VGetParser user = null;	
                // create proper html parser depends on url
                user = VGet.parser(web);
                // download maximum video quality from youtube
                user = new YouTubeMPGParser();

                // create proper videoinfo to keep specific video information
                VideoInfo info = user.info(web);

                VGet v = new VGet(info,new File( path));

                // [OPTIONAL] call v.extract() only if you d like to get video title
                // or download url link
                // before start download. or just skip it.
                v.extract();
                System.out.println("Title: " + info.getTitle());
                System.out.println("Download URL: " + info.getInfo().getSource());
                try {
					DownloadHttps dl = new DownloadHttps(info.getInfo().getSource(),path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		     
		 } else {
			 System.out.println("ungültiger link!");
		 }
       
       
	}
	public static boolean validYoutube(String ytUrl) {
	    String vId = null;
	    Pattern pattern = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
	    Matcher matcher = pattern.matcher(ytUrl);
	    if (matcher.matches()){
	        vId = matcher.group(1);
	    }
	    if(vId == null || vId.equals(new String(""))) {
	    	return false;
	    } else {
	    	return true;
	    }
	}

}
