package com.game.game_object;

import java.applet.AudioClip;
import java.awt.Rectangle;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;

import java.awt.Graphics2D;

public class Naruto extends Human {

  public static final int RUNSPEED = 3;

  private Animation runForwardAnim, runBackAnim, runShootingForwarAnim, runShootingBackAnim;
  private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
  private Animation dickForwardAnim, dickBackAnim;
  private Animation flyForwardAnim, flyBackAnim, flyShootingForwardAnim, flyShootingBackAnim;
  private Animation landingForwardAnim, landingBackAnim;

  private Animation climWallForward, climWallBack;

  private long lastShootingTime;
  private boolean isShooting = false;

  private AudioClip hurtingSound;
  private AudioClip shooting1;

  public Naruto(float x, float y, GameWorld gameWorld) {
    super(x, y, 75, 90, 0.1f, 100, gameWorld);

    shooting1 = CacheDataLoader.getInstance().getSound("rasengan");
    hurtingSound = CacheDataLoader.getInstance().getSound("dattebayo");

    setTeamType(LEAGUE_TEAM);

    setTimeForNoBehurt(2000 * 1000000); // 2s bất tử sau hồi sinh

    runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
    runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
    runBackAnim.flipAllImage();

    idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
    idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
    idleBackAnim.flipAllImage();

    dickForwardAnim = CacheDataLoader.getInstance().getAnimation("dick");
    dickBackAnim = CacheDataLoader.getInstance().getAnimation("dick");
    dickBackAnim.flipAllImage();

    flyForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
    flyForwardAnim.setIsRepeated(false);
    flyBackAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
    flyBackAnim.setIsRepeated(false);
    flyBackAnim.flipAllImage();

    landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
    landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
    landingBackAnim.flipAllImage();

    climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
    climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
    climWallForward.flipAllImage();

    behurtForwardAnim = CacheDataLoader.getInstance().getAnimation("hurting");
    behurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurting");
    behurtBackAnim.flipAllImage();

    idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
    idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
    idleShootingBackAnim.flipAllImage();

    runShootingForwarAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
    runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
    runShootingBackAnim.flipAllImage();

    flyShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
    flyShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
    flyShootingBackAnim.flipAllImage();

  }

  @Override
  public void update() {

    super.update();

    if (this.isShooting) {
      if (System.nanoTime() - lastShootingTime > 500 * 1000000) { // hết thời gian viên đạn tồn tại 0.5s mới đc bắn tiếp
        this.isShooting = false;
      }
    }

    if (getIsLanding()) {
      this.landingBackAnim.update(System.nanoTime());
      if (this.landingBackAnim.isLastFrame()) {
        this.setIsLanding(false);
        this.landingBackAnim.reset();
        this.runForwardAnim.reset();
        this.runBackAnim.reset();
      }
    }

  }

  @Override
  public Rectangle getBoundForCollisionWithEnemy() {
    Rectangle rect = getBoundForCollisionWithMap();

    if (getIsDicking()) {
      rect.x = (int) getPosX() - 22;
      rect.y = (int) getPosY() - 20;
      rect.width = 44;
      rect.height = 65; // ne chieu
    } else {
      rect.x = (int) getPosX() - 22;
      rect.y = (int) getPosY() - 40;
      rect.width = 44;
      rect.height = 80;
    }

    return rect;
  }

  @Override
  public void draw(Graphics2D g2) {

    switch (getState()) {

      case ALIVE:
      case NOBEHURT:
        if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) { // % 2 để tạo hiệu ứng nhấp nháy
          System.out.println("Plash...");
        } else {

          if (getIsLanding()) {

            if (getDirection() == RIGHT_DIR) {
              landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
              landingForwardAnim.draw((int) (this.getPosX() -
                  this.getGameWorld().camera.getPosX()),
                  (int) this.getPosY() - (int) this.getGameWorld().camera.getPosY() +
                      (this.getBoundForCollisionWithMap().height / 2 -
                          landingForwardAnim.getCurrentImage().getHeight() / 2),
                  g2);
            } else {
              landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                  (int) getPosY() - (int) getGameWorld().camera.getPosY() +
                      (getBoundForCollisionWithMap().height / 2 -
                          landingBackAnim.getCurrentImage().getHeight() / 2),
                  g2);
            }

          } else if (getIsJumping()) {

            if (getDirection() == RIGHT_DIR) {
              flyForwardAnim.update(System.nanoTime());
              if (isShooting) {
                flyShootingForwardAnim.setCurrentFrame(flyForwardAnim.getCurrentFrame());
                flyShootingForwardAnim.draw((int) (getPosX() -
                    getGameWorld().camera.getPosX()) + 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              } else
                flyForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                    (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
              flyBackAnim.update(System.nanoTime());
              if (isShooting) {
                flyShootingBackAnim.setCurrentFrame(flyBackAnim.getCurrentFrame());
                flyShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX())
                    - 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              } else
                flyBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                    (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }

          } else if (getIsDicking()) {

            if (getDirection() == RIGHT_DIR) {
              dickForwardAnim.update(System.nanoTime());
              dickForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                  (int) getPosY() - (int) getGameWorld().camera.getPosY() +
                      (getBoundForCollisionWithMap().height / 2 -
                          dickForwardAnim.getCurrentImage().getHeight() / 2),
                  g2);
            } else {
              dickBackAnim.update(System.nanoTime());
              dickBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                  (int) getPosY() - (int) getGameWorld().camera.getPosY() +
                      (getBoundForCollisionWithMap().height / 2 -
                          dickBackAnim.getCurrentImage().getHeight() / 2),
                  g2);
            }

          } else {
            if (getSpeedX() > 0) {
              runForwardAnim.update(System.nanoTime());
              if (isShooting) {
                runShootingForwarAnim.setCurrentFrame(runForwardAnim.getCurrentFrame() - 1);
                runShootingForwarAnim.draw((int) (getPosX() -
                    getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              } else
                runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                    (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              if (runForwardAnim.getCurrentFrame() == 1)
                runForwardAnim.setIgnoreFrame(0);
            } else if (getSpeedX() < 0) {
              runBackAnim.update(System.nanoTime());
              if (isShooting) {
                runShootingBackAnim.setCurrentFrame(runBackAnim.getCurrentFrame() - 1);
                runShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                    (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              } else
                runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                    (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
              if (runBackAnim.getCurrentFrame() == 1)
                runBackAnim.setIgnoreFrame(0);
            } else {
              if (getDirection() == RIGHT_DIR) {
                if (isShooting) {
                  idleShootingForwardAnim.update(System.nanoTime());
                  idleShootingForwardAnim.draw((int) (getPosX() -
                      getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                } else {
                  idleForwardAnim.update(System.nanoTime());
                  idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                      (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
              } else {
                if (isShooting) {
                  idleShootingBackAnim.update(System.nanoTime());
                  idleShootingBackAnim.draw((int) (getPosX() -
                      getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                } else {
                  idleBackAnim.update(System.nanoTime());
                  idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                      (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
              }
            }
          }
        }

        break;

      case BEHURT:
        if (getDirection() == RIGHT_DIR) {
          behurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        } else {
          behurtBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
          behurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
              (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        break;

      case FEY:

        break;

    }

    // drawBoundForCollisionWithMap(g2);
    // drawBoundForCollisionWithEnemy(g2);
  }

  @Override
  public void run() {
    if (getDirection() == LEFT_DIR)
      setSpeedX(-3);
    else
      setSpeedX(3);
  }

  @Override
  public void jump() {

    if (!getIsJumping()) {
      setIsJumping(true);
      setSpeedY(-5.0f);
      flyBackAnim.reset();
      flyForwardAnim.reset();
    }
    // ko bay -> leo tường:
    else {
      Rectangle rectRightWall = getBoundForCollisionWithMap();
      rectRightWall.x += 1;
      Rectangle rectLeftWall = getBoundForCollisionWithMap();
      rectLeftWall.x -= 1;

      if (this.getGameWorld().physicalMap.haveCollisionWithRightWall(rectRightWall) != null && getSpeedX() > 0) {
        setSpeedY(-5.0f);
        // setSpeedX(-1);
        flyBackAnim.reset();
        flyForwardAnim.reset();
        // setDirection(LEFT_DIR);
      } else if (this.getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeftWall) != null && getSpeedX() < 0) {
        setSpeedY(-5.0f);
        // setSpeedX(1);
        flyBackAnim.reset();
        flyForwardAnim.reset();
        // setDirection(RIGHT_DIR);
      }

    }
  }

  @Override
  public void dick() {
    if (!getIsJumping())
      setIsDicking(true);
  }

  @Override
  public void standUp() {
    this.setIsDicking(false);
    this.idleForwardAnim.reset();
    this.idleBackAnim.reset();
    this.dickForwardAnim.reset();
    this.dickBackAnim.reset();
  }

  @Override
  public void stopRun() {
    this.setSpeedX(0);
    this.runForwardAnim.reset();
    this.runBackAnim.reset();
    this.runForwardAnim.unIgnoreFrame(0);
    this.runBackAnim.unIgnoreFrame(0);
  }

  @Override
  public void attack() {
    if (!isShooting && !getIsDicking()) { // khác đang bắn or đang ngồi

      shooting1.play();

      Bullet bullet = new BlueBullet(getPosX(), getPosY(), getGameWorld());
      if (getDirection() == LEFT_DIR) {
        // đứng yên bắn
        bullet.setSpeedX(-10);
        bullet.setPosX(bullet.getPosX() - 40);
        // xét trường hợp đang vừa chạy vừa bắn
        if (getSpeedX() != 0 && getSpeedY() == 0) {
          bullet.setPosX(bullet.getPosX() - 10);
          bullet.setPosY(bullet.getPosY() - 5);
        }
      } else {
        bullet.setSpeedX(10);
        bullet.setPosX(bullet.getPosX() + 40);
        if (getSpeedX() != 0 && getSpeedY() == 0) {
          bullet.setPosX(bullet.getPosX() + 10);
          bullet.setPosY(bullet.getPosY() - 5);
        }
      }
      if (getIsJumping()) {
        // 20 = nhảy 5 + chiều cao đến tay nhân vật 15
        bullet.setPosY(bullet.getPosY() - 20);
      }

      bullet.setTeamType(getTeamType()); // làm đau địch, ko gây sát thương với đồng minh
      getGameWorld().bulletManager.addObject(bullet);

      this.lastShootingTime = System.nanoTime();
      this.isShooting = true;

    }
  }

  @Override
  public void hurtingCallback() {
    System.out.println("Call back hurting");
    hurtingSound.play();
  }
}
