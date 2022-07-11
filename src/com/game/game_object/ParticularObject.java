package com.game.game_object;

// import com.game.state.GameWorldState;
import com.game.effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject {

  public static final int LEAGUE_TEAM = 1; // team mình
  public static final int ENEMY_TEAM = 2; // team địch

  public static final int LEFT_DIR = 0;
  public static final int RIGHT_DIR = 1;

  // trạng thái nhân vật
  public static final int ALIVE = 0; // còn sống
  public static final int BEHURT = 1; // bị đau : trúng sát thương từ kẻ địch
  public static final int FEY = 2; // trạng thái sắp chết (báo đỏ màn hình)
  public static final int DEATH = 3; // chết
  public static final int NOBEHURT = 4; // trạng thái bất tử tạm thời sau hồi sinh
  private int state = ALIVE; // mặc định là còn sống

  private float width;
  private float height;
  private float mass;
  private float speedX;
  private float speedY;
  private int blood;

  private int damage;

  private int direction;

  protected Animation behurtForwardAnim, behurtBackAnim;

  private int teamType; // team nào

  private long startTimeNoBeHurt; // thời gian bắt đầu đau
  private long timeForNoBeHurt; // thời gian ko nhận sát thương

  public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {

    // posX and posY are the middle coordinate of the object
    super(x, y, gameWorld);
    setWidth(width);
    setHeight(height);
    setMass(mass);
    setBlood(blood);

    direction = RIGHT_DIR;

  }

  public void setTimeForNoBehurt(long time) {
    timeForNoBeHurt = time;
  }

  public long getTimeForNoBeHurt() {
    return timeForNoBeHurt;
  }

  public void setState(int state) {
    this.state = state;
  }

  public int getState() {
    return state;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getDamage() {
    return damage;
  }

  public void setTeamType(int team) {
    teamType = team;
  }

  public int getTeamType() {
    return teamType;
  }

  public void setMass(float mass) {
    this.mass = mass;
  }

  public float getMass() {
    return mass;
  }

  public void setSpeedX(float speedX) {
    this.speedX = speedX;
  }

  public float getSpeedX() {
    return speedX;
  }

  public void setSpeedY(float speedY) {
    this.speedY = speedY;
  }

  public float getSpeedY() {
    return speedY;
  }

  public void setBlood(int blood) {
    if (blood >= 0)
      this.blood = blood;
    else
      this.blood = 0;
  }

  public int getBlood() {
    return blood;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getWidth() {
    return width;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public float getHeight() {
    return height;
  }

  public void setDirection(int dir) {
    direction = dir;
  }

  public int getDirection() {
    return direction;
  }

  public abstract void attack();

  public boolean isObjectOutOfCameraView() {
    if (this.getPosX() - this.getGameWorld().camera.getPosX() > this.getGameWorld().camera.getWidthView() ||
        this.getPosX() - this.getGameWorld().camera.getPosX() < -50
        || this.getPosY() - this.getGameWorld().camera.getPosY() > this.getGameWorld().camera.getHeightView()
        || this.getPosY() - this.getGameWorld().camera.getPosY() < -50)
      return true;
    else
      return false;
  }

  public Rectangle getBoundForCollisionWithMap() {
    Rectangle bound = new Rectangle();
    bound.x = (int) (getPosX() - (getWidth() / 2));
    bound.y = (int) (getPosY() - (getHeight() / 2));
    bound.width = (int) getWidth();
    bound.height = (int) getHeight();
    return bound;
  }

  public void beHurt(int damgeEat) {
    setBlood(getBlood() - damgeEat);
    this.state = BEHURT;
    hurtingCallback();
  }

  @Override
  public void update() {
    switch (state) {
      case ALIVE:

        ParticularObject object = this.getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if (object != null) {

          if (object.getDamage() > 0) {

            // đổi trạng thái sang fey nếu object bị die

            System.out.println("eat damage.... from collision with enemy........ " +
                object.getDamage());
            beHurt(object.getDamage());
          }

        }

        break;

      case BEHURT:
        if (this.behurtBackAnim == null) {
          this.state = NOBEHURT;
          this.startTimeNoBeHurt = System.nanoTime();
          if (getBlood() == 0)
            this.state = FEY;

        } else {
          this.behurtForwardAnim.update(System.nanoTime());
          if (this.behurtForwardAnim.isLastFrame()) {
            this.behurtForwardAnim.reset();
            this.state = NOBEHURT;
            if (getBlood() == 0)
              this.state = FEY;
            this.startTimeNoBeHurt = System.nanoTime();
          }
        }

        break;

      case FEY: // tạm thời bỏ chức năng này => chuyển về chết luôn 😥

        this.state = DEATH;

        break;

      case DEATH:

        this.state = DEATH;
        break;

      case NOBEHURT:
        System.out.println("state = nobehurt");
        if (System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt)
          state = ALIVE;
        break;
    }

  }

  public void drawBoundForCollisionWithMap(Graphics2D g2) {
    Rectangle rect = getBoundForCollisionWithMap();
    g2.setColor(Color.BLUE);
    g2.drawRect(rect.x - (int) this.getGameWorld().camera.getPosX(),
        rect.y - (int) this.getGameWorld().camera.getPosY(), rect.width, rect.height);
    // g2.drawRect(rect.x, rect.y, rect.width, rect.height); test
  }

  public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
    Rectangle rect = getBoundForCollisionWithEnemy();
    g2.setColor(Color.RED);
    g2.drawRect(rect.x - (int) this.getGameWorld().camera.getPosX(),
        rect.y - (int) this.getGameWorld().camera.getPosY(), rect.width, rect.height);
    // g2.drawRect(rect.x, rect.y, rect.width, rect.height); test
  }

  public abstract Rectangle getBoundForCollisionWithEnemy();

  public abstract void draw(Graphics2D g2);

  public void hurtingCallback() {
  };

}
