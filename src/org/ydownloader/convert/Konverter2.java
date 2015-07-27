package org.ydownloader.convert;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class Konverter2 {
	
	public Konverter2()  {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IllegalArgumentException, InputFormatException, EncoderException {
		
		File source = new File("C:/DH/testsorce.mp4");
		File target = new File("C:/DH/testdes.mp3");
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
		System.out.println("Durch gegangen");
	}

}
