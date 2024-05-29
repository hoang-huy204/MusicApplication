/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
//import it.sauronsoftware.jave.*;

/**
 *
 * @author ngoch
 */
public class AudioRecorder {

    private static final int BUFFER_SIZE = 4096;

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private File outputFile;
    private boolean isRecording;
    private boolean isRecorded;
//    private boolean isPaused;

//    private XYChart.Series<Number, Number> waveformSeries = new XYChart.Series<>();
//
//    public XYChart.Series<Number, Number> getWaveformSeries() {
//        return waveformSeries;
//    }

    public AudioRecorder(File outputFile) {
        this.outputFile = outputFile;
        audioFormat = getAudioFormat();
    }

    public void startRecording() {
        if (!isRecording) {
            isRecorded = true;
            System.out.println("startRecording");
            try {
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                targetDataLine.open(audioFormat);
                targetDataLine.start();

                audioInputStream = new AudioInputStream(targetDataLine);

                new Thread(() -> {
                    isRecording = true;
                    int bufferSize = 44100 * audioFormat.getFrameSize();
                    byte[] audioData = new byte[BUFFER_SIZE];
                    try {
                        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
//                        while (isRecording) {
//                            int bytesRead = audioInputStream.read(audioData, 0, audioData.length);
//                            if (bytesRead > 0) {
//                                byte[] audioBuffer = Arrays.copyOf(audioData, bytesRead);
//                                drawWaveform(audioBuffer);
//                            }
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

//    public void pauseRecording() {
//        if (isRecording && !isPaused) {
//            System.out.println("pauseRecording");
//            isPaused = true;
//            if (targetDataLine != null && targetDataLine.isOpen()) {
//                targetDataLine.stop();
//            } else {
//                System.out.println("targetDataLine is null");
//            }
//        }
//    }
//
//    public void resumeRecording() {
//        if (isRecording && isPaused) {
//            if (isRecording && isPaused) {
//                System.out.println("resumeRecording");
//                isPaused = false;
//                targetDataLine.start();
//            }
//        }
//    }

    public void stopRecording() {
        if (isRecorded) {
            System.out.println("stopRecording");
            isRecording = false;
            targetDataLine.stop();
            targetDataLine.close();
            try {
                audioInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

//    private void drawWaveform(byte[] audioBuffer) {
//        // Xóa dữ liệu hiện tại trong series
//        waveformSeries.getData().clear();
//
//        // Tạo các điểm dữ liệu cho biểu đồ sóng từ dữ liệu âm thanh
//        for (int i = 0; i < audioBuffer.length; i++) {
//            waveformSeries.getData().add(new XYChart.Data<>(i, audioBuffer[i]));
//        }
//    }
}
