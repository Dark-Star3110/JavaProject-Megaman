package com.game.effect;

import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;

public class Animation {

  private String name;
  private boolean isRepeated;
  private ArrayList<FrameImage> frameImages;
  private ArrayList<Boolean> ignoreFrames;
  private ArrayList<Double> delayFrames;
  private int currentFrame;
  private long beginTime;
  private boolean drawRectFrame;

  public Animation() {
    delayFrames = new ArrayList<>();
    beginTime = 0;
    currentFrame = 0;
    ignoreFrames = new ArrayList<>();
    frameImages = new ArrayList<>();
    drawRectFrame = false;
    isRepeated = true;
  }

  public Animation(Animation animation) {
    this.beginTime = animation.beginTime;
    this.currentFrame = animation.currentFrame;
    this.drawRectFrame = animation.drawRectFrame;
    this.isRepeated = animation.isRepeated;

    this.delayFrames = new ArrayList<>();
    for (Double d : animation.delayFrames) {
      this.delayFrames.add(d);
    }

    this.ignoreFrames = new ArrayList<>();
    for (Boolean b : animation.ignoreFrames) {
      this.ignoreFrames.add(b);
    }

    this.frameImages = new ArrayList<>();
    for (FrameImage f : animation.frameImages) {
      this.frameImages.add(new FrameImage(f));
    }
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsRepeated() {
    return this.isRepeated;
  }

  public void setIsRepeated(boolean isRepeated) {
    this.isRepeated = isRepeated;
  }

  public ArrayList<FrameImage> getFrameImages() {
    return this.frameImages;
  }

  public void setFrameImages(ArrayList<FrameImage> frameImages) {
    this.frameImages = frameImages;
  }

  public ArrayList<Boolean> getIgnoreFrames() {
    return this.ignoreFrames;
  }

  public void setIgnoreFrames(ArrayList<Boolean> ignoreFrames) {
    this.ignoreFrames = ignoreFrames;
  }

  public ArrayList<Double> getDelayFrames() {
    return this.delayFrames;
  }

  public void setDelayFrames(ArrayList<Double> delayFrames) {
    this.delayFrames = delayFrames;
  }

  public int getCurrentFrame() {
    return this.currentFrame;
  }

  public void setCurrentFrame(int currentFrame) {
    this.currentFrame = currentFrame;
  }

  public long getBeginTime() {
    return this.beginTime;
  }

  public void setBeginTime(long beginTime) {
    this.beginTime = beginTime;
  }

  public boolean getIsDrawRectFrame() {
    return this.drawRectFrame;
  }

  public void setDrawRectFrame(boolean drawRectFrame) {
    this.drawRectFrame = drawRectFrame;
  }

  // ============ xu ly loric

  public boolean getIsIgnoreFrame(int index) {
    return this.ignoreFrames.get(index);
  }

  public boolean unIgnoreFrame(int index) {
    return this.ignoreFrames.set(index, false);
  }

  public void reset() {
    this.currentFrame = 0;
    this.beginTime = 0;
    for (int i = 0; i < this.ignoreFrames.size(); i++) {
      this.ignoreFrames.set(i, false);
    }
  }

  public void addFrame(FrameImage frameImage, double timeToNext) {
    this.ignoreFrames.add(false);
    this.frameImages.add(frameImage);
    this.delayFrames.add(timeToNext);
  }

  public BufferedImage getCurrentImage() {
    return this.frameImages.get(this.currentFrame).getImage();
  }

  public void nextFrame() {
    if (this.currentFrame >= this.delayFrames.size() - 1) {
      if (this.isRepeated) {
        this.currentFrame = 0;
      }
    } else {
      currentFrame++;
    }
    if (this.ignoreFrames.get(this.currentFrame)) {
      nextFrame();
    }
  }

  public void update(long currentTime) {
    // System.out.println(currentTime);
    if (this.beginTime == 0)
      this.beginTime = currentTime;
    else {
      // goi ham update lien tuc trong vong while o gamePanel (tra ve void => ko
      // update frame) cho den khi khoang time lon hon time bi delay
      if (currentTime - this.beginTime > this.delayFrames.get(this.currentFrame)) {
        this.nextFrame();
        this.beginTime = currentTime;
      }
    }
  }

  public boolean isLastFrame() {
    if (this.currentFrame == this.frameImages.size() - 1) {
      return true;
    } else {
      return false;
    }
  }

  // lat nguoc frame : chuyen trang thai tu chay sang phai => chay sang trai
  public void flipAllImage() {
    for (int i = 0; i < frameImages.size(); i++) {

      BufferedImage image = frameImages.get(i).getImage();

      AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
      tx.translate(-image.getWidth(), 0);

      AffineTransformOp op = new AffineTransformOp(tx,
          AffineTransformOp.TYPE_BILINEAR);
      image = op.filter(image, null);

      frameImages.get(i).setImage(image);

    }
  }

  // hien thi
  public void draw(int x, int y, Graphics2D g) {
    BufferedImage image = this.getCurrentImage();

    g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
    if (this.drawRectFrame) {
      g.drawRect(x - image.getWidth() / 2, y - image.getHeight() / 2, image.getWidth(), image.getHeight());
    }
  }
}
