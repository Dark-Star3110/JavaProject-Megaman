package com.game.game_object;

import java.awt.Rectangle;

public abstract class Human extends ParticularObject {

  private boolean isJumping; // đang nhảy (trang thái trên không trung)

  private boolean isDicking; // đang ngồi (vô hiệu hóa bay,đi)

  private boolean isLanding; // đang cúi xuống (trạng thái sau khi bay -> tiếp đất)

  public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
    super(x, y, width, height, mass, blood, gameWorld);
    setState(ALIVE);
  }

  public abstract void run();

  public abstract void jump();

  public abstract void dick();

  public abstract void standUp();

  public abstract void stopRun();

  public boolean getIsJumping() {
    return isJumping;
  }

  public void setIsLanding(boolean b) {
    isLanding = b;
  }

  public boolean getIsLanding() {
    return isLanding;
  }

  public void setIsJumping(boolean isJumping) {
    this.isJumping = isJumping;
  }

  public boolean getIsDicking() {
    return isDicking;
  }

  public void setIsDicking(boolean isDicking) {
    this.isDicking = isDicking;
  }

  @Override
  public void update() {

    super.update();

    if (getState() == ALIVE || getState() == NOBEHURT) {

      if (!isLanding) {

        setPosX(getPosX() + getSpeedX());
        // đi quá -> nếu va chạm set lại x của nhân vật (thời gian update nhanh nên ko
        // thấy khoảng bị gián đoạn)
        if (this.getDirection() == LEFT_DIR &&
            this.getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {

          Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
          setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);

        }
        if (this.getDirection() == RIGHT_DIR &&
            getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {

          Rectangle rectRightWall = getGameWorld().physicalMap
              .haveCollisionWithRightWall(getBoundForCollisionWithMap());
          setPosX(rectRightWall.x - getWidth() / 2);

        }

        Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
        boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2); // +2 sai số
        Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);

        Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);

        if (rectTop != null) { // va chạm top trên map

          setSpeedY(0);
          setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight() / 2);

        } else if (rectLand != null) {
          setIsJumping(false);
          if (getSpeedY() > 0)
            setIsLanding(true);
          setSpeedY(0);
          setPosY(rectLand.y - getHeight() / 2 - 1);
        } else {
          setIsJumping(true);
          setSpeedY(getSpeedY() + getMass());
          setPosY(getPosY() + getSpeedY());
        }
      }
    }
  }

}
