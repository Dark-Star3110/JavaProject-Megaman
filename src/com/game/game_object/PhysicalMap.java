package com.game.game_object;

import com.game.effect.CacheDataLoader;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class PhysicalMap extends GameObject {

  public int[][] phys_map;
  private int tileSize;

  public PhysicalMap(float x, float y, GameWorld gameWorld) {
    super(x, y, gameWorld);
    this.tileSize = 100;
    phys_map = CacheDataLoader.getInstance().getPhysicalMap();
  }

  public int getTileSize() {
    return tileSize;
  }

  public void draw(Graphics2D g2) {

    Camera camera = getGameWorld().camera;

    g2.setColor(Color.GRAY);
    for (int i = 0; i < phys_map.length; i++) {
      for (int j = 0; j < phys_map[0].length; j++) {
        if (phys_map[i][j] != 0)
          g2.fillRect((int) this.getPosX() + j * tileSize - (int) camera.getPosX(),
              (int) this.getPosY() + i * tileSize - (int) camera.getPosY(), tileSize, tileSize);
      }
    }

  }

  // giống như xét va chạm với đất
  public Rectangle haveCollisionWithTop(Rectangle rect) {

    int posX1 = rect.x / tileSize;
    posX1 -= 2;
    int posX2 = (rect.x + rect.width) / tileSize;
    posX2 += 2;

    // int posY = (rect.y + rect.height)/tileSize;
    int posY = rect.y / tileSize;

    if (posX1 < 0)
      posX1 = 0;

    if (posX2 >= phys_map[0].length)
      posX2 = phys_map[0].length - 1;

    for (int y = posY; y >= 0; y--) {
      for (int x = posX1; x <= posX2; x++) {

        if (phys_map[y][x] == 1) {
          Rectangle r = new Rectangle((int) this.getPosX() + x * tileSize, (int) this.getPosY() + y * tileSize,
              tileSize,
              tileSize);
          if (rect.intersects(r))
            return r;
        }
      }
    }
    return null;
  }

  // param la 1 hinh chu nhat ( vung bao quanh nhan vat,quai,...)
  // return khoi chu nhat bi va cham trong map
  public Rectangle haveCollisionWithLand(Rectangle rect) {
    // posX1, posX2, posY1 la khoang goi han de duyet vong lap for xet xem va cham
    // voi khoi hinh chu nhat nao => toi uu vong for: duyet tu 0 den het map
    int posX1 = rect.x / tileSize;
    posX1 -= 2; // tru sai so
    int posX2 = (rect.x + rect.width) / tileSize;
    posX2 += 2;

    int posY1 = (rect.y + rect.height) / tileSize;

    if (posX1 < 0)
      posX1 = 0;

    if (posX2 >= phys_map[0].length)
      posX2 = phys_map[0].length - 1;
    for (int i = posY1; i < phys_map.length; i++) {
      for (int j = posX1; j <= posX2; j++) {

        if (phys_map[i][j] == 1) {
          Rectangle r = new Rectangle((int) this.getPosX() + j * tileSize, (int) this.getPosY() + i * tileSize,
              tileSize,
              tileSize);
          if (rect.intersects(r)) // va cham
            return r;
        }
      }
    }
    return null;
  }

  public Rectangle haveCollisionWithRightWall(Rectangle rect) {

    int posY1 = rect.y / tileSize;
    posY1 -= 2;
    int posY2 = (rect.y + rect.height) / tileSize;
    posY2 += 2;

    int posX1 = (rect.x + rect.width) / tileSize;
    int posX2 = posX1 + 3;
    if (posX2 >= phys_map[0].length)
      posX2 = phys_map[0].length - 1;

    if (posY1 < 0)
      posY1 = 0;
    if (posY2 >= phys_map.length)
      posY2 = phys_map.length - 1;

    for (int x = posX1; x <= posX2; x++) {
      for (int y = posY1; y <= posY2; y++) {
        if (phys_map[y][x] == 1) {
          Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize,
              tileSize);
          if (r.y < rect.y + rect.height - 1 && rect.intersects(r))
            return r;
        }
      }
    }
    return null;

  }

  public Rectangle haveCollisionWithLeftWall(Rectangle rect) {

    int posY1 = rect.y / tileSize;
    posY1 -= 2;
    int posY2 = (rect.y + rect.height) / tileSize;
    posY2 += 2;

    int posX1 = (rect.x + rect.width) / tileSize;
    int posX2 = posX1 - 3;
    if (posX2 < 0)
      posX2 = 0;

    if (posY1 < 0)
      posY1 = 0;
    if (posY2 >= phys_map.length)
      posY2 = phys_map.length - 1;

    for (int x = posX1; x >= posX2; x--) {
      for (int y = posY1; y <= posY2; y++) {
        if (phys_map[y][x] == 1) {
          Rectangle r = new Rectangle((int) this.getPosX() + x * this.tileSize,
              (int) this.getPosY() + y * this.tileSize, this.tileSize,
              this.tileSize);
          if (r.y < rect.y + rect.height - 1 && rect.intersects(r))
            return r;
        }
      }
    }
    return null;

  }

  @Override
  public void update() {
  }

}
