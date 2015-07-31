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
//Progress Variable um den Fortschritt anzuzeigen
public int progress;	
	public Konverter3()  {
		// TODO Auto-generated constructor stub
	}
	
	
	//Konvert Function die die Ausgangsdatei und den Zielspeicherort übergeben bekommt. Die Funktionen kommen aus dem Sauronsoftware package.
	public boolean Konvert (File source, String targetstream) throws IllegalArgumentException, InputFormatException, EncoderException{
		//File source = new File(sourcestream);
		File target = new File(targetstream);
		AudioAttributes audio = new AudioAttributes();
		//Im folgenden werden die Audioattribute für die Konvertierung gesetzt. U. a. der Codec und  die Bitrate
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		//Hier wird die Konvertierungsattribute gesetzt
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		//Ein progress Listener, namer erklärt seine Funktion
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
		//Nun wird ein Encoder Objekt erstellt und anschließend die Funktion Aufgerufen die sich um die Konvertierung
		//der Datei in MP3 kümmert.
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs,listen);
		return true;
	}

		
		
	
	public static void main(String[] args) throws InputFormatException, EncoderException {
		
				
	}

	

}

