package com.game.game_object;

import java.applet.AudioClip;
import java.awt.Graphics2D;

import com.game.effect.CacheDataLoader;
import com.game.ui.GameFrame;

public class GameWorld {

  public Megaman megaman;
  public PhysicalMap physicalMap;
  public BackgroundMap backgroundMap;
  public ParticularObjectManager particularObjectManager;
  public BulletManager bulletManager;
  public Camera camera;

  public AudioClip bgMusic;

  public GameWorld() {
    this.megaman = new Megaman(300, 300, this);
    this.megaman.setTeamType(ParticularObject.LEAGUE_TEAM);
    this.physicalMap = new PhysicalMap(0, 0, this);
    this.backgroundMap = new BackgroundMap(0, 0, this);
    this.camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
    this.particularObjectManager = new ParticularObjectManager(this);
    this.particularObjectManager.addObject(megaman);
    this.bulletManager = new BulletManager(this);

    initEnemies();

    bgMusic = CacheDataLoader.getInstance().getSound("bgmusic");
  }

  private void initEnemies() {
    ParticularObject redeye = new RedEyeDevil(1250, 410, this);
    redeye.setDirection(ParticularObject.LEFT_DIR);
    redeye.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(redeye);
  }

  public void update() {
    this.particularObjectManager.updateObjects();
    this.camera.update();
    this.bulletManager.updateObjects();
  }

  public void render(Graphics2D g2) {
    // physicalMap.draw(g2);
    backgroundMap.draw(g2);
    particularObjectManager.draw(g2);
    bulletManager.draw(g2);
  }
}
