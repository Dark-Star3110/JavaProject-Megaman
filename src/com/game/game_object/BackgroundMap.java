package com.game.game_object;

import com.game.effect.CacheDataLoader;
import com.game.ui.GameFrame;

// import java.awt.Color;
import java.awt.Graphics2D;

public class BackgroundMap extends GameObject {
  public int[][] map;
  private int tileSize;

  public BackgroundMap(float x, float y, GameWorld gameWorld) {
    super(x, y, gameWorld);
    map = CacheDataLoader.getInstance().getBackgroundMap();
    tileSize = 100;
  }

  @Override
  public void update() {
  }

  public void draw(Graphics2D g2) {

    Camera camera = getGameWorld().camera;

    // g2.setColor(Color.RED);
    for (int i = 0; i < map.length; i++)
      for (int j = 0; j < map[0].length; j++)
        // chỉ vẽ những vùng trong khung hình ( camera)
        if (map[i][j] != 0 && j * tileSize - camera.getPosX() > -tileSize
            && j * tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
            && i * tileSize - camera.getPosY() > -tileSize
            && i * tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT) {
          g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled" + map[i][j]).getImage(),
              (int) getPosX() + j * tileSize - (int) camera.getPosX(),
              (int) getPosY() + i * tileSize - (int) camera.getPosY(), null);
        }

  }

}
