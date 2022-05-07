package com.game.game_object;

public abstract class GameObject {
  private float posX;
  private float posY;
  private GameWorld gameWorld;

  public GameObject(float posX, float posY, GameWorld gameWorld) {
    this.posX = posX;
    this.posY = posY;
    this.gameWorld = gameWorld;
  }

  public float getPosX() {
    return this.posX;
  }

  public void setPosX(float posX) {
    this.posX = posX;
  }

  public float getPosY() {
    return this.posY;
  }

  public void setPosY(float posY) {
    this.posY = posY;
  }

  public GameWorld getGameWorld() {
    return this.gameWorld;
  }

  public void setGameWorld(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
  }

  public abstract void update();

}
