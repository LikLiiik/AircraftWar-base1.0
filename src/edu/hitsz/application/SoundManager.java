package edu.hitsz.application;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

/**
 * 音效管理器 - 基于 MusicThread 实现
 * 管理游戏中的所有音效和背景音乐
 * @author AircraftWar
 */
public class SoundManager {

    private static SoundManager instance;
    
    // 当前播放的 BGM 线程
    private Thread currentBGMThread;
    private MusicThread currentBGMMusicThread;
    private String currentBGMPath;
    
    // Boss 音乐线程
    private Thread bossBGMThread;
    private MusicThread bossBGMMusicThread;
    private String bossBGMPath;
    
    // 音效文件路径
    private static final String SOUND_PATH = "src/videos/";
    
    // 音效文件名
    private static final String BGM_FILE = "bgm.wav";
    private static final String BGM_BOSS_FILE = "bgm_boss.wav";
    private static final String BULLET_HIT_FILE = "bullet_hit.wav";
    private static final String BOMB_EXPLOSION_FILE = "bomb_explosion.wav";
    private static final String GET_SUPPLY_FILE = "get_supply.wav";
    private static final String GAME_OVER_FILE = "game_over.wav";
    
    private SoundManager() {
        // 保存音频文件路径
        currentBGMPath = SOUND_PATH + BGM_FILE;
        bossBGMPath = SOUND_PATH + BGM_BOSS_FILE;
        System.out.println("音效管理器初始化完成");
        System.out.println("BGM 路径: " + currentBGMPath);
        System.out.println("Boss BGM 路径: " + bossBGMPath);
    }
    
    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    /**
     * 播放背景音乐（循环）
     */
    public void playBGM() {
        System.out.println("开始播放 BGM");
        stopBGM();
        try {
            currentBGMMusicThread = new MusicThread(currentBGMPath);
            currentBGMMusicThread.loop = true;
            currentBGMThread = new Thread(currentBGMMusicThread);
            currentBGMThread.start();
            System.out.println("BGM 线程已启动");
        } catch (Exception e) {
            System.err.println("BGM 播放失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 播放 Boss 背景音乐（循环）
     */
    public void playBossBGM() {
        System.out.println("开始播放 Boss BGM");
        stopBGM();
        try {
            bossBGMMusicThread = new MusicThread(bossBGMPath);
            bossBGMMusicThread.loop = true;
            bossBGMThread = new Thread(bossBGMMusicThread);
            bossBGMThread.start();
            System.out.println("Boss BGM 线程已启动");
        } catch (Exception e) {
            System.err.println("Boss BGM 播放失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 停止背景音乐
     */
    public void stopBGM() {
        System.out.println("停止 BGM");
        if (currentBGMMusicThread != null) {
            currentBGMMusicThread.shouldStop = true;
        }
        if (currentBGMThread != null && currentBGMThread.isAlive()) {
            currentBGMThread.interrupt();
        }
        if (bossBGMMusicThread != null) {
            bossBGMMusicThread.shouldStop = true;
        }
        if (bossBGMThread != null && bossBGMThread.isAlive()) {
            bossBGMThread.interrupt();
        }
    }
    
    /**
     * 播放音效（不循环）
     */
    public void playSound(String fileName) {
        try {
            MusicThread soundThread = new MusicThread(SOUND_PATH + fileName);
            soundThread.setLoop(false);
            Thread thread = new Thread(soundThread);
            thread.start();
        } catch (Exception e) {
            System.err.println("音效播放失败：" + fileName);
        }
    }
    
    public void playBulletHit() {
        playSound(BULLET_HIT_FILE);
    }
    
    public void playBombExplosion() {
        playSound(BOMB_EXPLOSION_FILE);
    }
    
    public void playGetSupply() {
        playSound(GET_SUPPLY_FILE);
    }
    
    public void playGameOver() {
        stopBGM();
        playSound(GAME_OVER_FILE);
    }
}
