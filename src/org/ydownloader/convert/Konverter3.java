package org.ydownloader.convert;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncoderProgressListener;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;



import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

public class Konverter3   {
public int progress;	
	public Konverter3()  {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public boolean Konvert (File source, String targetstream) throws IllegalArgumentException, InputFormatException, EncoderException{
		//File source = new File(sourcestream);
		File target = new File(targetstream);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		EncoderProgressListener listen = new EncoderProgressListener() {
			
			@Override
			public void sourceInfo(MultimediaInfo arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void progress(int pr) {
				progress=pr;
				
			}
			
			@Override
			public void message(String arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs,listen);
		return true;
	}

		
		
	
	public static void main(String[] args) throws InputFormatException, EncoderException {
		
				
	}

	

}

