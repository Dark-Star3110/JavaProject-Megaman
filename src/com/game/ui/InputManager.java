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
        this.gameWorld.naruto.dick();
        break;
      case KeyEvent.VK_LEFT:
        // System.out.println("left");
        this.gameWorld.naruto.setDirection(ParticularObject.LEFT_DIR);
        this.gameWorld.naruto.run();
        // this.gameWorld.physicalMap.x -= 3;
        break;
      case KeyEvent.VK_RIGHT:
        // System.out.println("right");
        this.gameWorld.naruto.setDirection(ParticularObject.RIGHT_DIR);
        this.gameWorld.naruto.run();
        // this.gameWorld.physicalMap.x += 3;
        break;
      case KeyEvent.VK_ENTER:
        if (this.gameWorld.state == GameWorld.INIT_GAME) {
          if (this.gameWorld.previousState == GameWorld.GAMEPLAY) {
            this.gameWorld.switchState(GameWorld.GAMEPLAY);
          } else {
            this.gameWorld.switchState(GameWorld.TUTORIAL);
          }
          this.gameWorld.bgMusic.loop();
          this.gameWorld.bgMusic.play();
        }
        if (this.gameWorld.state == GameWorld.TUTORIAL && this.gameWorld.storyTutorial >= 1) {
          if (this.gameWorld.storyTutorial <= 3) {
            this.gameWorld.storyTutorial++;
            this.gameWorld.currentSize = 1;
            this.gameWorld.textTutorial = this.gameWorld.texts1[this.gameWorld.storyTutorial - 1];
          } else {
            this.gameWorld.switchState(GameWorld.GAMEPLAY);
          }

          // for meeting boss tutorial
          if (this.gameWorld.tutorialState == GameWorld.MEETFINALBOSS) {
            this.gameWorld.switchState(GameWorld.GAMEPLAY);
          }
        }
        break;
      case KeyEvent.VK_SPACE:
        this.gameWorld.naruto.jump();
        break;
      case KeyEvent.VK_A:
        this.gameWorld.naruto.attack();
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
        this.gameWorld.naruto.standUp();
        break;
      case KeyEvent.VK_LEFT:
        if (this.gameWorld.naruto.getSpeedX() < 0) {
          this.gameWorld.naruto.stopRun();
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (this.gameWorld.naruto.getSpeedX() > 0) {
          this.gameWorld.naruto.stopRun();
        }
        break;
      case KeyEvent.VK_ENTER:
        // if(this.gameWorld.state == GameWorld.GAMEOVER || this.gameWorld.state ==
        // GameWorld.GAMEWIN) {
        // gamePanel.setState(new MenuState(gamePanel));
        // } else if(this.gameWorld.state == PAUSEGAME) {
        // this.gameWorld.state = this.gameWorld.getLastState();
        // }
        break;
      case KeyEvent.VK_SPACE:
        // this.gameWorld.naruto.setSpeedY(0);
        break;
      case KeyEvent.VK_A:
        break;
    }
  }
}
