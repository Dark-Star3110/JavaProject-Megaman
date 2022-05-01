package com.game.game_object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Megaman {

  private float posX;
  private float posY;

  private float width;
  private float height;
  private float mass;

  private float speedX;
  private float speedY;

  public static int DIR_LEFT;
  public static int DIR_RIGHT;
  private int direction;

  public Megaman(float posX, float posY, float width, float height, float mass) {
    this.posX = posX;
    this.posY = posY;
    this.width = width;
    this.height = height;
    this.mass = mass;
  }

  public float getSpeedX() {
    return this.speedX;
  }

  public void setSpeedX(float speedX) {
    this.speedX = speedX;
  }

  public float getSpeedY() {
    return this.speedY;
  }

  public void setSpeedY(float speedY) {
    this.speedY = speedY;
  }

  public int getDirection() {
    return this.direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
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

  public float getWidth() {
    return this.width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return this.height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public float getMass() {
    return this.mass;
  }

  public void setMass(float mass) {
    this.mass = mass;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(Color.red);
    g2.fillRect((int) posX, (int) posY, (int) width, (int) height);
  }

  public void update() {
    this.setPosX(this.getPosX() + this.speedX);
    if (this.getPosY() >= 400) {
      this.setPosY(400);
    } else {
      this.setPosY(this.getPosY() + this.speedY);
      this.setSpeedY(this.getSpeedY() + this.mass);
    }
  }
}
