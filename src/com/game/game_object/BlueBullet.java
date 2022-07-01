package com.game.game_object;

import java.awt.Rectangle;
import java.awt.Graphics2D;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

public class BlueBullet extends Bullet {

  private Animation forwardBulletAnim, backBulletAnim;

  public BlueBullet(float x, float y, GameWorld gameWorld) {
    super(x, y, 60, 30, 1.0f, 10, gameWorld);
    forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluebullet");
    backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluebullet");
    backBulletAnim.flipAllImage();
  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    return this.getBoundForCollisionWithMap();
  }

  @Override
  public void draw(Graphics2D g2) {

    if (getSpeedX() > 0) {
      // theo image megasprite thì 3 frame đầu là ảnh đạn nổ lúc ra khỏi nòng => nổ 1
      // lần rồi ignore nó đi,chỉ lặp lại animation đạn bay
      if (!forwardBulletAnim.isIgnoreFrame(0) &&
          forwardBulletAnim.getCurrentFrame() == 1) {
        forwardBulletAnim.setIgnoreFrame(0);
      }

      forwardBulletAnim.update(System.nanoTime());
      forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
          (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    } else {
      if (!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 1) {
        backBulletAnim.setIgnoreFrame(0);
      }
      backBulletAnim.update(System.nanoTime());
      backBulletAnim.draw((int) (this.getPosX() - getGameWorld().camera.getPosX()),
          (int) this.getPosY() - (int) getGameWorld().camera.getPosY(), g2);
    }
    // drawBoundForCollisionWithEnemy(g2);
  }

  public void update() {
    // 0 được ignore => đạn bắt đầu bắn
    if (forwardBulletAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0))
      setPosX(getPosX() + getSpeedX());
    ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
    if (object != null && object.getState() == ALIVE) {
      setBlood(0);
      object.setBlood(object.getBlood() - getDamage());
      object.setState(BEHURT);
      System.out.println("Bullet set behurt for enemy");
    }
  }

  @Override
  public void attack() {
  }

}
