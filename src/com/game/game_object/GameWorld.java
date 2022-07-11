package com.game.game_object;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import com.game.effect.CacheDataLoader;
import com.game.effect.FrameImage;
import com.game.ui.GameFrame;

public class GameWorld {

  private BufferedImage bufferedImage;
  private int lastState;

  public Naruto naruto;
  public PhysicalMap physicalMap;
  public BackgroundMap backgroundMap;
  public ParticularObjectManager particularObjectManager;
  public BulletManager bulletManager;
  public Camera camera;

  public static final int finalBossY = 3600;

  public static final int INIT_GAME = 0;
  public static final int TUTORIAL = 1;
  public static final int GAMEPLAY = 2;
  public static final int GAMEOVER = 3;
  public static final int GAMEWIN = 4;
  public static final int PAUSEGAME = 5;

  public static final int INTROGAME = 0;
  public static final int MEETFINALBOSS = 1;

  public int openIntroGameY = 0;
  public int state = INIT_GAME;
  public int previousState = state;
  public int tutorialState = INTROGAME;

  public int storyTutorial = 0;
  public String[] texts1 = new String[4];

  public String textTutorial;
  public int currentSize = 1;

  private boolean finalbossTrigger = true;
  ParticularObject boss;

  FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");

  private int numberOfLife = 3;

  public AudioClip bgMusic;

  public GameWorld() {

    texts1[0] = "Chào mừng các bạn đến với game của nhóm 5:\n Naruto's Adventure 😭....";
    texts1[1] = "Lũ quái vật đã xâm chiếm ngồi làng của Naruto\n"
        + "sử dụng phím mũi tên trái phải để di chuyển, cách để nhảy \nvà phím A để sử dụng kĩ năng....";
    texts1[2] = "Cố gắng giết thật nhiều quái vật và hoàn thành trò chơi.\n Naruto-kun!....";
    texts1[3] = "      ÉT O ÉT!.....";
    textTutorial = texts1[0];

    bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    this.naruto = new Naruto(300, 120, this);
    this.naruto.setTeamType(ParticularObject.LEAGUE_TEAM);
    this.physicalMap = new PhysicalMap(0, 0, this);
    this.backgroundMap = new BackgroundMap(0, 0, this);
    this.camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
    this.particularObjectManager = new ParticularObjectManager(this);
    this.particularObjectManager.addObject(naruto);
    this.bulletManager = new BulletManager(this);

    initEnemies();

    bgMusic = CacheDataLoader.getInstance().getSound("gamesong");
  }

  private void initEnemies() {
    ParticularObject spider = new Spider(1400, 360, this);
    spider.setDirection(ParticularObject.LEFT_DIR);
    spider.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider);

    ParticularObject spider2 = new Spider(1550, 860, this);
    spider2.setDirection(ParticularObject.LEFT_DIR);
    spider2.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider2);

    ParticularObject spider3 = new Spider(1850, 1260, this);
    spider3.setDirection(ParticularObject.LEFT_DIR);
    spider3.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider3);

    ParticularObject spider4 = new Spider(1150, 2060, this);
    spider4.setDirection(ParticularObject.RIGHT_DIR);
    spider4.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider4);

    ParticularObject spider5 = new Spider(750, 2360, this);
    spider5.setDirection(ParticularObject.LEFT_DIR);
    spider5.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider5);

    ParticularObject spider6 = new Spider(1850, 2760, this);
    spider6.setDirection(ParticularObject.LEFT_DIR);
    spider6.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(spider6);

    // // bug
    ParticularObject bug = new Bug(1750, 650, this);
    bug.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bug);

    ParticularObject bug2 = new Bug(1350, 2250, this);
    bug2.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bug2);

    // // bat
    ParticularObject bat = new Bat(600, 180, this);
    bat.setDirection(ParticularObject.LEFT_DIR);
    bat.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bat);

    ParticularObject bat1 = new Bat(860, 1100, this);
    bat1.setDirection(ParticularObject.LEFT_DIR);
    bat1.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bat1);

    // // bird

    ParticularObject bird = new Bird(600, 800, this);
    bird.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bird);

    ParticularObject bird2 = new Bird(1200, 1600, this);
    bird2.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bird2);

    ParticularObject bird3 = new Bird(750, 3160, this);
    bird3.setTeamType(ParticularObject.ENEMY_TEAM);
    particularObjectManager.addObject(bird3);

  }

  public void switchState(int state) {
    previousState = this.state;
    this.state = state;
  }

  private void Tutorialupdate() {
    switch (tutorialState) {
      case INTROGAME:

        if (storyTutorial == 0) {
          if (openIntroGameY < 450) {
            openIntroGameY += 4;
          } else
            storyTutorial++;

        } else {

          if (currentSize < textTutorial.length())
            currentSize++;
        }
        break;
      case MEETFINALBOSS:
        if (storyTutorial == 0) {
          System.out.println("meeting boss");
          if (openIntroGameY >= 450) {
            openIntroGameY -= 1;
          }
          if (camera.getPosY() < finalBossY) {
            camera.setPosY(camera.getPosY() + 1);
            camera.setPosX(camera.getPosX() + 1.6F);
          }

          if (naruto.getPosY() < finalBossY + 100) {
            naruto.setDirection(ParticularObject.RIGHT_DIR);
            naruto.run();
            naruto.update();
          } else {
            naruto.stopRun();
          }

          if (camera.getPosY() >= finalBossY - 250 && naruto.getPosY() >= finalBossY) {
            System.out.println("lock");
            camera.lock();
            storyTutorial++;
            naruto.stopRun();

            physicalMap.phys_map[35][10] = 1;
            backgroundMap.map[35][10] = 20;
          }

        } else {
          if (currentSize < textTutorial.length()) {
            currentSize++;
          }
        }
        break;
    }
  }

  private void drawString(Graphics2D g2, String text, int x, int y) {
    for (String str : text.split("\n"))
      g2.drawString(str, x, y += g2.getFontMetrics().getHeight());
  }

  private void TutorialRender(Graphics2D g2) {
    switch (tutorialState) {
      case INTROGAME:
        int yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
        int y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
        int y2 = yMid + openIntroGameY / 2;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
        g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);

        if (storyTutorial >= 1) {
          g2.drawImage(avatar.getImage(), 600, 240, null);
          g2.setColor(Color.WHITE);
          g2.fillRect(280, 450, 350, 80);
          g2.setColor(Color.BLACK);
          String text = textTutorial.substring(0, currentSize - 1);
          drawString(g2, text, 290, 480);
        }

        break;
      case MEETFINALBOSS:
        yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
        y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
        y2 = yMid + openIntroGameY / 2;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
        g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
        break;
    }
  }

  public void update() {

    switch (state) {
      case INIT_GAME:

        break;
      case PAUSEGAME:
        break;
      case TUTORIAL:
        Tutorialupdate();

        break;
      case GAMEPLAY:
        particularObjectManager.updateObjects();
        bulletManager.updateObjects();

        physicalMap.update();
        camera.update();

        if (naruto.getPosY() > finalBossY && finalbossTrigger) {
          finalbossTrigger = false;
          switchState(TUTORIAL);
          tutorialState = MEETFINALBOSS;
          storyTutorial = 0;
          openIntroGameY = 550;

          boss = new FinalBoss(1700, finalBossY + 100, this);
          boss.setTeamType(ParticularObject.ENEMY_TEAM);
          boss.setDirection(ParticularObject.LEFT_DIR);
          particularObjectManager.addObject(boss);

        }

        if (naruto.getState() == ParticularObject.DEATH) {
          numberOfLife--;
          if (numberOfLife >= 0) {
            naruto.setBlood(100);
            naruto.setPosY(naruto.getPosY() - 50);
            naruto.setState(ParticularObject.NOBEHURT);
            particularObjectManager.addObject(naruto);
          } else {
            switchState(GAMEOVER);
            bgMusic.stop();
          }
        }
        if (!finalbossTrigger && boss.getState() == ParticularObject.DEATH)
          switchState(GAMEWIN);

        break;
      case GAMEOVER:

        break;
      case GAMEWIN:

        break;
    }

  }

  public void render() {

    Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

    if (g2 != null) {
      // g2.setColor(Color.WHITE);
      // g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);

      // physicalMap.draw(g2);

      switch (state) {
        case INIT_GAME:
          // System.out.println("init");
          g2.setColor(Color.BLACK);
          g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
          g2.setColor(Color.WHITE);
          g2.drawString("NHẤN ENTER ĐỂ TIẾP TỤC", 400, 300);
          break;
        case PAUSEGAME:
          g2.setColor(Color.BLACK);
          g2.fillRect(300, 260, 500, 70);
          g2.setColor(Color.WHITE);
          g2.drawString("NHẤN ENTER ĐỂ TIẾP TỤC", 400, 300);
          break;
        case TUTORIAL:
          backgroundMap.draw(g2);
          if (tutorialState == MEETFINALBOSS) {
            particularObjectManager.draw(g2);
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage("finalboss").getImage(), 350, 60, null);
            g2.drawString("NHẤN ENTER ĐỂ TIẾP TỤC", 400, 300);
          }
          TutorialRender(g2);

          break;
        case GAMEWIN:
        case GAMEPLAY:
          backgroundMap.draw(g2);
          particularObjectManager.draw(g2);
          bulletManager.draw(g2);

          g2.setColor(Color.GRAY);
          g2.fillRect(19, 59, 102, 22);
          g2.setColor(Color.red);
          g2.fillRect(20, 60, naruto.getBlood(), 20);

          for (int i = 0; i < numberOfLife; i++) {
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i * 40, 18, null);
          }

          if (!finalbossTrigger) {
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(359, 29, 302, 22);
            g2.setColor(Color.orange);
            g2.fillRect(360, 30, boss.getBlood(), 20);
          }

          if (state == GAMEWIN) {
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 220, 80, null);
          }

          break;
        case GAMEOVER:
          g2.setColor(Color.BLACK);
          g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
          g2.setColor(Color.WHITE);
          g2.drawString("GAME OVER!", 450, 300);
          break;

      }

    }

  }

  public BufferedImage getBufferedImage() {
    return this.bufferedImage;
  }

}
