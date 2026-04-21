package edu.hitsz.application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

/**
 * 音乐播放线程 - 基于参考代码改进
 * 支持循环播放和停止功能
 * @author AircraftWar
 */
public class MusicThread extends Thread {

    // 音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    
    // 循环播放标志
    public boolean loop = false;
    // 停止标志
    public boolean shouldStop = false;

    public MusicThread(String filename) {
        // 初始化 filename
        this.filename = filename;
        reverseMusic();
    }
    
    /**
     * 设置是否循环播放
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
    /**
     * 停止音乐播放
     */
    public void stopMusic() {
        shouldStop = true;
    }

    public void reverseMusic() {
        try {
            // 定义一个 AudioInputStream 用于接收输入的音频数据，使用 AudioSystem 来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            // 用 AudioFormat 来获取 AudioInputStream 的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        // 源数据行 SoureDataLine 是可以写入数据的数据行
        SourceDataLine dataLine = null;
        // 获取受数据行支持的音频格式 DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (!shouldStop) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                } else {
                    // 音频播放完毕
                    if (loop) {
                        // 重新创建输入流，继续循环播放
                        source = new ByteArrayInputStream(samples);
                    } else {
                        // 不循环，退出播放
                        break;
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();
    }

    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        play(stream);
    }
}
