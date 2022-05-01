package com.game.effect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class CacheDataLoader {

  private static CacheDataLoader instance = null;

  private String frameFile = "data/frame.txt";
  private String animationFile = "data/animation.txt";
  private String physmapFile = "data/phys_map.txt";

  private Hashtable<String, FrameImage> frameImages;
  private Hashtable<String, Animation> animations;

  private int[][] phys_map;

  private CacheDataLoader() {
  }

  public static CacheDataLoader getInstance() {
    if (instance == null) {
      instance = new CacheDataLoader();
    }
    return instance;
  }

  public void LoadData() throws IOException {
    this.LoadFrame();
    this.LoadAnimation();
    this.LoadPhysMap();
  }

  public Animation getAnimation(String name) {

    Animation animation = new Animation(instance.animations.get(name));
    return animation;

  }

  public FrameImage getFrameImage(String name) {

    FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
    return frameImage;

  }

  public int[][] getPhysicalMap() {
    return instance.phys_map;
  }

  public int[][] getPhys_map() {
    return this.phys_map;
  }

  public void setPhys_map(int[][] phys_map) {
    this.phys_map = phys_map;
  }

  public void LoadAnimation() throws IOException {

    animations = new Hashtable<String, Animation>();

    FileReader fr = new FileReader(animationFile);
    BufferedReader br = new BufferedReader(fr);

    String line = null;

    if (br.readLine() == null) {
      System.out.println("No data");
      throw new IOException();
    } else {

      fr = new FileReader(animationFile);
      br = new BufferedReader(fr);

      while ((line = br.readLine()).equals(""))
        ;
      int n = Integer.parseInt(line);

      for (int i = 0; i < n; i++) {

        Animation animation = new Animation();

        while ((line = br.readLine()).equals(""))
          ;
        animation.setName(line);

        while ((line = br.readLine()).equals(""))
          ;
        String[] str = line.split(" ");

        for (int j = 0; j < str.length; j += 2)
          animation.addFrame(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));

        instance.animations.put(animation.getName(), animation);

      }

    }

    br.close();
  }

  public void LoadFrame() throws IOException {

    frameImages = new Hashtable<String, FrameImage>();

    FileReader fr = new FileReader(frameFile);
    BufferedReader br = new BufferedReader(fr);

    String line = null;

    if (br.readLine() == null) {
      System.out.println("No data");
      throw new IOException();
    } else {

      fr = new FileReader(frameFile);
      br = new BufferedReader(fr);

      while ((line = br.readLine()).equals(""))
        ;

      int n = Integer.parseInt(line);
      String path = null;
      BufferedImage imageData = null;
      // int i2 = 0;
      for (int i = 0; i < n; i++) {

        FrameImage frame = new FrameImage();
        while ((line = br.readLine()).equals(""))
          ;
        // System.out.println(line);
        frame.setName(line);

        while ((line = br.readLine()).equals(""))
          ;
        String[] str = line.split(" ");

        boolean refreshImage = (path == null || !path.equals(str[1]));
        path = str[1];
        // System.out.println(path);

        while ((line = br.readLine()).equals(""))
          ;
        str = line.split(" ");
        int x = Integer.parseInt(str[1]);

        while ((line = br.readLine()).equals(""))
          ;
        str = line.split(" ");
        int y = Integer.parseInt(str[1]);

        while ((line = br.readLine()).equals(""))
          ;
        str = line.split(" ");
        int w = Integer.parseInt(str[1]);

        while ((line = br.readLine()).equals(""))
          ;
        str = line.split(" ");
        int h = Integer.parseInt(str[1]);

        if (refreshImage) {
          refreshImage = false;
          imageData = ImageIO.read(new File(path));
        }
        if (imageData != null) {
          BufferedImage image = imageData.getSubimage(x, y, w, h);
          frame.setImage(image);
        }

        instance.frameImages.put(frame.getName(), frame);
      }

    }

    br.close();

  }

  public void LoadPhysMap() throws IOException {

    FileReader fr = new FileReader(physmapFile);
    BufferedReader br = new BufferedReader(fr);

    String line = null;

    line = br.readLine();
    int numberOfRows = Integer.parseInt(line);
    line = br.readLine();
    int numberOfColumns = Integer.parseInt(line);

    instance.phys_map = new int[numberOfRows][numberOfColumns];

    for (int i = 0; i < numberOfRows; i++) {
      line = br.readLine();
      String[] str = line.split(" ");
      for (int j = 0; j < numberOfColumns; j++)
        instance.phys_map[i][j] = Integer.parseInt(str[j]);
    }

    for (int i = 0; i < numberOfRows; i++) {

      for (int j = 0; j < numberOfColumns; j++)
        System.out.print(" " + instance.phys_map[i][j]);

      System.out.println();
    }

    br.close();

  }

}
