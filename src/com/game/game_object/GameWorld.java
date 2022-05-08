package com.game.game_object;

import java.awt.Graphics2D;

import com.game.ui.GameFrame;

public class GameWorld {

  public Megaman megaman;
  public PhysicalMap physicalMap;
  public ParticularObjectManager particularObjectManager;
  public BulletManager bulletManager;
  public Camera camera;

  public GameWorld() {
    this.megaman = new Megaman(300, 300, this);
    this.physicalMap = new PhysicalMap(0, 0, this);
    this.camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
    this.particularObjectManager = new ParticularObjectManager(this);
    this.bulletManager = new BulletManager(this);
  }

  public void update() {
    this.megaman.update();
    this.camera.update();
    this.particularObjectManager.updateObjects();
    this.bulletManager.updateObjects();
  }

  public void render(Graphics2D g2) {
    physicalMap.draw(g2);
    megaman.draw(g2);
    bulletManager.draw(g2);
  }
}
