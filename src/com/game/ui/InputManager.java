package com.game.ui;

import java.awt.event.KeyEvent;

public class InputManager {

  public void processKeyPressed(int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_UP:
        System.out.println("up");
        break;
      case KeyEvent.VK_DOWN:
        System.out.println("down");
        break;
      case KeyEvent.VK_LEFT:
        System.out.println("left");
        break;
      case KeyEvent.VK_RIGHT:
        System.out.println("right");
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        break;
      case KeyEvent.VK_A:
        break;
    }
  }

  public void processKeyReleased(int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_UP:
        System.out.println("up");
        break;
      case KeyEvent.VK_DOWN:
        System.out.println("down");
        break;
      case KeyEvent.VK_LEFT:
        System.out.println("left");
        break;
      case KeyEvent.VK_RIGHT:
        System.out.println("right");
        break;
      case KeyEvent.VK_ENTER:
        break;
      case KeyEvent.VK_SPACE:
        break;
      case KeyEvent.VK_A:
        break;
    }
  }
}
