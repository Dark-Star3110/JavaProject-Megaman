package com.game.game_object;

import java.util.Hashtable;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class FinalBoss extends Human {
  private Animation idleforward, idleback;
  private Animation shootingforward, shootingback;
  private Animation raikiriforward, raikiriback;

  private long startTimeForAttacked;

  private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();
  private String[] attackType = new String[4];
  private int attackIndex = 0;
  private long lastAttackTime;

  public FinalBoss(float x, float y, GameWorld gameWorld) {
    super(x, y, 75, 90, 0.1f, 200, gameWorld);
    idleback = CacheDataLoader.getInstance().getAnimation("kakashi_idle");
    idleback.flipAllImage();
    idleforward = CacheDataLoader.getInstance().getAnimation("kakashi_idle");

    shootingback = CacheDataLoader.getInstance().getAnimation("kakashi_shoot");
    shootingback.flipAllImage();
    shootingforward = CacheDataLoader.getInstance().getAnimation("kakashi_shoot");

    raikiriback = CacheDataLoader.getInstance().getAnimation("raikiri");
    raikiriback.flipAllImage();
    raikiriforward = CacheDataLoader.getInstance().getAnimation("raikiri");

    setTimeForNoBehurt(500 * 1000000);
    setDamage(10);

    attackType[0] = "NONE";
    attackType[1] = "shooting";
    attackType[2] = "NONE";
    attackType[3] = "raikiri";

    timeAttack.put("NONE", 2000L);
    timeAttack.put("shooting", 500L);
    timeAttack.put("raikiri", 5000L);

  }

  public void update() {
    super.update();

    if (getGameWorld().naruto.getPosX() > getPosX())
      setDirection(RIGHT_DIR);
    else
      setDirection(LEFT_DIR);

    if (startTimeForAttacked == 0)
      startTimeForAttacked = System.currentTimeMillis();
    else if (System.currentTimeMillis() - startTimeForAttacked > 300) {
      attack();
      startTimeForAttacked = System.currentTimeMillis();
    }

    if (!attackType[attackIndex].equals("NONE")) {
      if (attackType[attackIndex].equals("shooting")) {

        Bullet bullet = new Shuriken(getPosX(), getPosY() - 50, getGameWorld());
        if (getDirection() == LEFT_DIR)
          bullet.setSpeedX(-4);
        else
          bullet.setSpeedX(4);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

      } else if (attackType[attackIndex].equals("raikiri")) {

        if (getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null)
          setSpeedX(5);
        if (getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null)
          setSpeedX(-5);

        setPosX(getPosX() + getSpeedX());
      }
    } else {
      // stop attack
      setSpeedX(0);
    }

  }

  @Override
  public void run() {

  }

  @Override
  public void jump() {
    setSpeedY(-5);
  }

  @Override
  public void dick() {

  }

  @Override
  public void standUp() {

  }

  @Override
  public void stopRun() {

  }

  @Override
  public void attack() {

    // only switch state attack

    if (System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])) {
      lastAttackTime = System.currentTimeMillis();

      attackIndex++;
      if (attackIndex >= attackType.length)
        attackIndex = 0;

      if (attackType[attackIndex].equals("raikiri")) {
        if (getPosX() < getGameWorld().naruto.getPosX())
          setSpeedX(5);
        else
          setSpeedX(-5);
      }

    }

  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    if (attackType[attackIndex].equals("raikiri")) {
      Rectangle rect = getBoundForCollisionWithMap();
      rect.y += 50;
      rect.height -= 50;
      return rect;
    } else
      return getBoundForCollisionWithMap();
  }

  @Override
  public void draw(Graphics2D g2) {

    if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
      System.out.println("Plash...");
    } else {

      if (attackType[attackIndex].equals("NONE")) {
        if (getDirection() == RIGHT_DIR) {
          idleforward.update(System.nanoTime());
          idleforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        } else {
          idleback.update(System.nanoTime());
          idleback.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
      } else if (attackType[attackIndex].equals("shooting")) {

        if (getDirection() == RIGHT_DIR) {
          shootingforward.update(System.nanoTime());
          shootingforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        } else {
          shootingback.update(System.nanoTime());
          shootingback.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }

      } else if (attackType[attackIndex].equals("raikiri")) {
        if (getSpeedX() > 0) {
          raikiriforward.update(System.nanoTime());
          raikiriforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY() + 10, g2);
        } else {
          raikiriback.update(System.nanoTime());
          raikiriback.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY() + 10, g2);
        }
      }
    }
    // drawBoundForCollisionWithEnemy(g2);
  }

}
