package com.game.game_object;

// lớp này để bắt vị trí nhân vật -> hiển thị khung nhìn map cho nhân vật
public class Camera extends GameObject {

  private float widthView;
  private float heightView;

  private boolean isLocked = false;

  public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
    super(x, y, gameWorld);
    this.widthView = widthView;
    this.heightView = heightView;
  }

  public void lock() {
    this.isLocked = true;
  }

  public void unlock() {
    this.isLocked = false;
  }

  @Override
  public void update() {

    // khi gặp boss cuối camera sẽ ko thay đổi vị trí (đứng yên tại khung hình chiến
    // boss)
    if (!isLocked) {

      Megaman mainCharacter = getGameWorld().megaman;
      // 200 và 400 là khoảng tối ưu để camera theo dõi nhân vật
      if (mainCharacter.getPosX() - this.getPosX() > 400)
        this.setPosX(mainCharacter.getPosX() - 400);
      if (mainCharacter.getPosX() - this.getPosX() < 200)
        this.setPosX(mainCharacter.getPosX() - 200);

      if (mainCharacter.getPosY() - this.getPosY() > 400)
        this.setPosY(mainCharacter.getPosY() - 400); // bottom
      else if (mainCharacter.getPosY() - getPosY() < 250)
        this.setPosY(mainCharacter.getPosY() - 250);// top
    }

  }

  public float getWidthView() {
    return this.widthView;
  }

  public void setWidthView(float widthView) {
    this.widthView = widthView;
  }

  public float getHeightView() {
    return this.heightView;
  }

  public void setHeightView(float heightView) {
    this.heightView = heightView;
  }

}
