package com.game.game_object;

import com.game.effect.CacheDataLoader;

import java.awt.Graphics2D;
import java.awt.Color;

public class PhysicalMap {

  public int[][] phys_map;
  private int tileSize;

  public float x, y;

  public PhysicalMap(float x, float y) {
    this.x = x;
    this.y = y;
    this.tileSize = 30;
    phys_map = CacheDataLoader.getInstance().getPhysicalMap();
  }

  public int getTileSize() {
    return tileSize;
  }

  public void draw(Graphics2D g2) {

    g2.setColor(Color.GRAY);
    for (int i = 0; i < phys_map.length; i++) {
      for (int j = 0; j < phys_map[0].length; j++) {

        if (phys_map[i][j] != 0)
          g2.fillRect((int) x + j * tileSize,
              (int) y + i * tileSize, tileSize, tileSize);
      }
    }

  }

}
