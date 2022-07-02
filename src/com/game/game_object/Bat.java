package com.game.game_object;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Bat extends ParticularObject {
  private Animation forwardAnim, backAnim;
  private long startTimeToShoot;

  public Bat(float x, float y, GameWorld gameWorld) {
    super(x, y, 127, 89, 0, 100, gameWorld);
    backAnim = CacheDataLoader.getInstance().getAnimation("bat");
    forwardAnim = CacheDataLoader.getInstance().getAnimation("bat");
    forwardAnim.flipAllImage();
    startTimeToShoot = 0;
    setTimeForNoBehurt(300000000);
  }

  @Override
  public void attack() {

    Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
    bullet.setSpeedX(-3);
    bullet.setSpeedY(3);
    bullet.setTeamType(getTeamType());
    getGameWorld().bulletManager.addObject(bullet);

    bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
    bullet.setSpeedX(3);
    bullet.setSpeedY(3);
    bullet.setTeamType(getTeamType());
    getGameWorld().bulletManager.addObject(bullet);
  }

  public void update() {
    super.update();
    if (System.nanoTime() - startTimeToShoot > 1000 * 10000000 * 2.0) {
      attack();
      System.out.println("Red Eye attack");
      startTimeToShoot = System.nanoTime();
    }
  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    Rectangle rect = getBoundForCollisionWithMap();
    rect.x += 20;
    rect.width -= 40;

    return rect;
  }

  @Override
  public void draw(Graphics2D g2) {
    if (!isObjectOutOfCameraView()) {
      if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
        // plash...
      } else {
        if (getDirection() == LEFT_DIR) {
          backAnim.update(System.nanoTime());
          backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        } else {
          forwardAnim.update(System.nanoTime());
          forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
      }
    }
    // drawBoundForCollisionWithEnemy(g2);
  }
}
