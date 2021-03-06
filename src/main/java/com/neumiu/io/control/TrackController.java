package com.neumiu.io.control;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.neumiu.io.utils.FlacAnalyzer;
/**
 * The TrackController Class
 * @author NeuMiu Team
 */
public class TrackController {
	/**
	 * TrackController Default Constructor
	 */
	public TrackController() {}

	/**
	 * Gets the total time in Milliseconds for the duration of the Playing song, formats it as a String, and returns said String.
	 * @param song (File)
 	 * @return runTime - Milliseconds in String (in terms of MM:SS)
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public String getTotalTime(File song) throws UnsupportedAudioFileException, IOException {
		String runTime = null;
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(song);
		AudioFormat format = audioInputStream.getFormat();
		if (format.toString().contains("MPEG1L3")) {
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(song);
			if (fileFormat instanceof AudioFileFormat) {
				Map<?, ?> properties = ((AudioFileFormat) fileFormat).properties();
				String key = "duration";
				Long microseconds = (Long) properties.get(key);
				int mili = (int) (microseconds / 1000);
				int sec = (mili / 1000) % 60;
				int min = (mili / 1000) / 60;
				String secString = String.format("%02d", sec);
				runTime = min + ":" + secString;
			} else {
				throw new UnsupportedAudioFileException();
			}
		} else if (format.toString().contains("FLAC")) {
			FlacAnalyzer lookUp = new FlacAnalyzer();
			double seconds = lookUp.analyse(song);
			int sec = (int) seconds % 60;
			int min = (int) seconds / 60;
			String secString = String.format("%02d", sec);
			runTime = min + ":" + secString;
		} else if (format.toString().contains("VORBIS")) {
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(song);
			if (fileFormat instanceof AudioFileFormat) {
				Map<?, ?> properties = ((AudioFileFormat) fileFormat).properties();
				String key = "duration";
				Long microseconds = (Long) properties.get(key);
				int mili = (int) (microseconds / 1000);
				int sec = (mili / 1000) % 60;
				int min = (mili / 1000) / 60;
				String secString = String.format("%02d", sec);
				runTime = min + ":" + secString;
			} else {
				throw new UnsupportedAudioFileException();
			}
		} else if (format.toString().contains("PCM")) {
			double length = song.length();
			double sampleRate = format.getSampleRate();
			double channels = format.getChannels();
			double audioSampleSize = format.getSampleSizeInBits();
			System.out.println("Lenght: " + length + ", Sample Rate: " + sampleRate + ", Channels: " + channels
					+ ", Audio Sample Size in Bits: " + audioSampleSize);
			double time = length / (sampleRate * channels * (audioSampleSize / 8));
			int timeInSec = (int) time;
			int sec = timeInSec % 60;
			int min = timeInSec / 60;
			String secString = String.format("%02d", sec);
			runTime = min + ":" + secString;
		}
		return runTime;
	}

	/**
	 * Gets the total Time in Milliseconds for the Duration of the Song.
	 * @param song (File)
	 * @return runTime - Total Time in Milliseconds
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public long getTotalTimeMillis(File song) throws UnsupportedAudioFileException, IOException {
		long runTime = 0;
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(song);
		AudioFormat format = audioInputStream.getFormat();
		if (format.toString().contains("MPEG1L3")) {
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(song);
			if (fileFormat instanceof AudioFileFormat) {
				Map<?, ?> properties = ((AudioFileFormat) fileFormat).properties();
				String key = "duration";
				Long microseconds = (Long) properties.get(key);
				int mili = (int) (microseconds / 1000);
				runTime = mili;
			} else {
				throw new UnsupportedAudioFileException();
			}
		} else if (format.toString().contains("FLAC")) {
			FlacAnalyzer lookUp = new FlacAnalyzer();
			double seconds = lookUp.analyse(song);
			runTime = (long) (seconds * 1000);
		} else if (format.toString().contains("VORBIS")) {
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(song);
			if (fileFormat instanceof AudioFileFormat) {
				Map<?, ?> properties = ((AudioFileFormat) fileFormat).properties();
				String key = "duration";
				Long microseconds = (Long) properties.get(key);
				int mili = (int) (microseconds / 1000);
				runTime = mili;
			} else {
				throw new UnsupportedAudioFileException();
			}
		} else if (format.toString().contains("PCM")) {
			double length = song.length();
			double sampleRate = format.getSampleRate();
			double channels = format.getChannels();
			double audioSampleSize = format.getSampleSizeInBits();
			double time = length / (sampleRate * channels * (audioSampleSize / 8));
			runTime = (long) (time * 1000);
		}
		return runTime;
	}
}
