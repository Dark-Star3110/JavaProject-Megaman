package com.game.game_object;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Shuriken extends Bullet {

  private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
  private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

  private long startTimeForChangeSpeedY;

  public Shuriken(float x, float y, GameWorld gameWorld) {

    super(x, y, 30, 30, 1.0f, 10, gameWorld);

    backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("shurikenUp");
    backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("shurikenDown");
    backBulletAnim = CacheDataLoader.getInstance().getAnimation("shuriken");

    forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("shurikenUp");
    forwardBulletAnimUp.flipAllImage();
    forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("shurikenDown");
    forwardBulletAnimDown.flipAllImage();
    forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("shuriken");
    forwardBulletAnim.flipAllImage();

  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    return getBoundForCollisionWithMap();
  }

  @Override
  public void draw(Graphics2D g2) {
    if (getSpeedX() > 0) {
      if (getSpeedY() > 0) {
        forwardBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
      } else if (getSpeedY() < 0) {
        forwardBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
      } else
        forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    } else {
      if (getSpeedY() > 0) {
        backBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
      } else if (getSpeedY() < 0) {
        backBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
      } else
        backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    }
    // drawBoundForCollisionWithEnemy(g2);
  }

  private void changeSpeedY() {
    // random đạn
    if (System.currentTimeMillis() % 3 == 0) {
      setSpeedY(getSpeedX());
    } else if (System.currentTimeMillis() % 3 == 1) {
      setSpeedY(-getSpeedX());
    } else {
      setSpeedY(0);

    }
  }

  @Override
  public void update() {
    super.update();

    if (System.nanoTime() - startTimeForChangeSpeedY > 500 * 1000000) {
      startTimeForChangeSpeedY = System.nanoTime();
      changeSpeedY();
    }
  }

  @Override
  public void attack() {
  }
}
