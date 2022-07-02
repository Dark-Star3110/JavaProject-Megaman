package com.game.game_object;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class VioletBullet extends Bullet {
  private Animation forwardBulletAnim, backBulletAnim;

  public VioletBullet(float x, float y, GameWorld gameWorld) {
    super(x, y, 60, 30, 1.0f, 10, gameWorld);
    forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("violetbullet");
    backBulletAnim = CacheDataLoader.getInstance().getAnimation("violetbullet");
    backBulletAnim.flipAllImage();
  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    // TODO Auto-generated method stub
    return getBoundForCollisionWithMap();
  }

  @Override
  public void draw(Graphics2D g2) {
    // TODO Auto-generated method stub
    if (getSpeedX() > 0) {
      forwardBulletAnim.update(System.nanoTime());
      forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
          (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    } else {
      backBulletAnim.update(System.nanoTime());
      backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
          (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    }
    // drawBoundForCollisionWithEnemy(g2);
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    super.update();
  }

  @Override
  public void attack() {
  }
}
