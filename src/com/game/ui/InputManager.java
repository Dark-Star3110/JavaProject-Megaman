package com.game.ui;

import java.awt.event.KeyEvent;

import com.game.game_object.GameWorld;
import com.game.game_object.ParticularObject;

public class InputManager {

  private GameWorld gameWorld;

  public InputManager(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
  }

  public void processKeyPressed(int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_UP:
        // System.out.println("up");
        // this.gameWorld.physicalMap.y -= 3;
        break;
      case KeyEvent.VK_DOWN:
        // System.out.println("down");
        // this.gameWorld.physicalMap.y += 3;
        break;
      case KeyEvent.VK_LEFT:
        // System.out.println("left");
        this.gameWorld.megaman.setDirection(ParticularObject.LEFT_DIR);
        this.gameWorld.megaman.run();
        // this.gameWorld.physicalMap.x -= 3;
        break;
      case KeyEvent.VK_RIGHT:
        // System.out.println("right");
        this.gameWorld.megaman.setDirection(ParticularObject.RIGHT_DIR);
        this.gameWorld.megaman.run();
        // this.gameWorld.physicalMap.x += 3;
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        this.gameWorld.megaman.jump();
        break;
      case KeyEvent.VK_A:
        this.gameWorld.megaman.attack();
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
        this.gameWorld.megaman.standUp();
        break;
      case KeyEvent.VK_LEFT:
        if (this.gameWorld.megaman.getSpeedX() < 0) {
          this.gameWorld.megaman.stopRun();
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (this.gameWorld.megaman.getSpeedX() > 0) {
          this.gameWorld.megaman.stopRun();
        }
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        // this.gameWorld.megaman.setSpeedY(0);
        break;
      case KeyEvent.VK_A:
        break;
    }
  }
}
