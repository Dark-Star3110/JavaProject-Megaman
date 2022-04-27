package com.game.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {
  private String name;
  private BufferedImage image;

  public FrameImage(String name, BufferedImage image) {
    this.name = name;
    this.image = image;
  }

  public FrameImage(FrameImage frameImage) {
    this.image = new BufferedImage(frameImage.getImageWidth(),
        frameImage.getImageHeight(), frameImage.image.getType());
    Graphics g = image.getGraphics();
    g.drawImage(frameImage.image, 0, 0, null);
    name = frameImage.name;
  }

  public void draw(Graphics2D g2, int x, int y) {
    // System.out.println(this.image);
    g2.drawImage(this.image, x - this.image.getWidth() / 2, y - this.image.getHeight() / 2, null);
  }

  public FrameImage() {
    this.name = null;
    this.image = null;
  }

  public int getImageWidth() {
    return image.getWidth();
  }

  public int getImageHeight() {
    return image.getHeight();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BufferedImage getImage() {
    return this.image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

}
