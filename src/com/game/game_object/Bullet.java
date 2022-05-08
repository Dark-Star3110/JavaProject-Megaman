package com.game.game_object;

import java.awt.Graphics2D;

public abstract class Bullet extends ParticularObject {

  public Bullet(float x, float y, float width, float height, float mass, int damage, GameWorld gameWorld) {
    super(x, y, width, height, mass, 1, gameWorld);
    this.setDamage(damage);
  }

  public abstract void draw(Graphics2D g2);

  @Override
  public void update() {
    super.update();
    this.setPosX(this.getPosX() + this.getSpeedX());
    this.setPosY(this.getPosY() + this.getSpeedY());
    // ParticularObject object =
    // getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
    // if(object!=null && object.getState() == ALIVE){
    // setBlood(0);
    // object.beHurt(getDamage());
    // System.out.println("Bullet set behurt for enemy");
    // }
  }

}
