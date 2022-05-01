package com.game.ui;

import java.awt.event.KeyEvent;

import com.game.game_object.Megaman;

public class InputManager {

  private GamePanel gamePanel;

  public InputManager(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public void processKeyPressed(int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_UP:
        // System.out.println("up");
        this.gamePanel.physicalMap.y -= 3;
        break;
      case KeyEvent.VK_DOWN:
        // System.out.println("down");
        this.gamePanel.physicalMap.y += 3;
        break;
      case KeyEvent.VK_LEFT:
        // System.out.println("left");
        this.gamePanel.megaman.setDirection(Megaman.DIR_LEFT);
        this.gamePanel.megaman.setSpeedX(-5);
        this.gamePanel.physicalMap.x -= 3;
        break;
      case KeyEvent.VK_RIGHT:
        // System.out.println("right");
        this.gamePanel.megaman.setDirection(Megaman.DIR_RIGHT);
        this.gamePanel.megaman.setSpeedX(5);
        this.gamePanel.physicalMap.x += 3;
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        this.gamePanel.megaman.setSpeedY(-3);
        this.gamePanel.megaman.setPosY(this.gamePanel.megaman.getPosY() - 3);
        break;
      case KeyEvent.VK_A:
        break;
    }
  }

  public void processKeyReleased(int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_UP:
        // System.out.println("up");
        break;
      case KeyEvent.VK_DOWN:
        // System.out.println("down");
        break;
      case KeyEvent.VK_LEFT:
        this.gamePanel.megaman.setSpeedX(0);
        break;
      case KeyEvent.VK_RIGHT:
        this.gamePanel.megaman.setSpeedX(0);
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        // this.gamePanel.megaman.setSpeedY(0);
        break;
      case KeyEvent.VK_A:
        break;
    }
  }
}
