package com.seamcarver.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seamcarver.demo.models.SeamCarver;

import edu.princeton.cs.algs4.Picture;

@Controller
public class DemoController {
  private String picturePath = "/picture/chameleon.png";
  
  private String resizedPath = "/picture/chameleon_resized.png";
  
  private Picture picture = new Picture("upload/chameleon.png");
  
  private Picture resized = new Picture("upload/chameleon_resized.png");
  
  private int originalWidth = this.picture.width();
  
  private int originalHeight = this.picture.height();
  
  private int newWidth = this.resized.width();
  
  private int newHeight = this.resized.height();
  
  @GetMapping({"/"})
  public String index(Model model) {
    model.addAttribute("picturePath", this.picturePath);
    model.addAttribute("originalWidth", Integer.valueOf(this.originalWidth));
    model.addAttribute("originalHeight", Integer.valueOf(this.originalHeight));
    model.addAttribute("resizedPath", this.resizedPath);
    model.addAttribute("newWidth", Integer.valueOf(this.newWidth));
    model.addAttribute("newHeight", Integer.valueOf(this.newHeight));
    return "index";
  }
  
  @PostMapping({"/resize"})
  public String resize(Model model, @RequestParam("targetWidth") int targetWidth, @RequestParam("targetHeight") int targetHeight) {
    if (targetWidth > this.picture.width() || targetHeight > this.picture.height())
      throw new IllegalArgumentException("target size exceeding original size!"); 
    this.newWidth = targetWidth;
    this.newHeight = targetHeight;
    SeamCarver sc = new SeamCarver(this.picture);
    int i;
    for (i = this.originalHeight; i > this.newHeight; i--) {
      int[] horizontalSeam = sc.findHorizontalSeam();
      sc.removeHorizontalSeam(horizontalSeam);
    } 
    for (i = this.originalWidth; i > this.newWidth; i--) {
      int[] verticalSeam = sc.findVerticalSeam();
      sc.removeVerticalSeam(verticalSeam);
    } 
    this.resized = sc.picture();
    this.resized.save("upload/chameleon_resized.png");
    return "redirect:/";
  }
}