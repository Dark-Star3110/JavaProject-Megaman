package com.game.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.game.effect.CacheDataLoader;

public class GameFrame extends JFrame {

  public static final int SCREEN_WIDTH = 1000;
  public static final int SCREEN_HEIGHT = 600;

  GamePanel gamePanel;

  public GameFrame() {
    Toolkit toolkit = this.getToolkit();
    Dimension dimension = toolkit.getScreenSize(); // lay kich thuoc man hinh
    this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH,
        SCREEN_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // tat chuong trinh khi bam nut close

    try {
      CacheDataLoader.getInstance().LoadData();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    gamePanel = new GamePanel();
    this.add(gamePanel);
    this.addKeyListener(gamePanel);
  }

  public void startGame() {
    gamePanel.startGame();
  }

  public static void main(String[] args) {
    GameFrame gameFrame = new GameFrame();
    gameFrame.setVisible(true); // show man hinh game
    gameFrame.startGame();
  }
}
