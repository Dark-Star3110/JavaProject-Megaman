package com.game.game_object;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Rectangle;
import java.applet.AudioClip;
import java.awt.Graphics2D;

public class Spider extends ParticularObject {
  private Animation forwardAnim, backAnim;

  private long startTimeToShoot;

  private AudioClip shooting;

  public Spider(float x, float y, GameWorld gameWorld) {
    super(x, y, 127, 89, 0, 100, gameWorld);
    backAnim = CacheDataLoader.getInstance().getAnimation("spider");
    forwardAnim = CacheDataLoader.getInstance().getAnimation("spider");
    forwardAnim.flipAllImage();
    startTimeToShoot = 0;
    setDamage(10);
    setTimeForNoBehurt(300000000); // bất tử 0.3s
    shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
  }

  @Override
  public void attack() {

    shooting.play();
    Bullet bullet = new BugBullet(this.getPosX(), this.getPosY(), this.getGameWorld());
    if (getDirection() == LEFT_DIR)
      bullet.setSpeedX(-8);
    else
      bullet.setSpeedX(8);
    bullet.setTeamType(this.getTeamType());
    this.getGameWorld().bulletManager.addObject(bullet);

  }

  public void update() {
    super.update();
    if (System.nanoTime() - startTimeToShoot > 1000 * 10000000) { // 1s bắn 1 phát
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
