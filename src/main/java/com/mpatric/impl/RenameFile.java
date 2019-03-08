package com.mpatric.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class RenameFile {
	
	private static final String MP3 = ".mp3";

	public static void main(String[] args) {
		
		File originalDirectory = new File( Paths.get(".").toAbsolutePath().normalize().toString() + "/");
		
		File[] files = originalDirectory.listFiles();
		
		ArrayList<String> musicNames = new ArrayList<String>();
		
		for(int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if(name.contains(MP3))
				musicNames.add(name);
		}
		
		musicNames.forEach((name)-> {
			try {
				ID3v1 tag = new Mp3File(originalDirectory + "/" + name).getId3v1Tag();
				
				File file = new File(originalDirectory + "/" + name);
				
				// when you have more than one artist the replaceAll prevents errors
				File rename = new File(originalDirectory + "/" + tag.getTitle() + " - " + tag.getArtist().replaceAll("/", " & ") + MP3);
				
				System.out.println( "File " + file.getName() + " renamed to " + rename.getName() );
				
				file.renameTo(rename);
				
			} catch (UnsupportedTagException e) {
				System.out.println( "Error to recognizing the tag " + e.getMessage() );
			} catch (InvalidDataException e) {
				System.out.println( "Error acknowledging input data " + e.getMessage() );
			} catch (IOException e) {
				System.out.println( "IO Error " + e.getMessage() );
			}
			
		});
		 
	}

}
