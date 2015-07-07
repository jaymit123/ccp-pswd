/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.io;

import java.awt.Image;
import java.util.List;
import java.util.LinkedList;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author VJ
 */
public class ImageModel {

    private static String Path;
    private List<String> ImageList;
    private File ImgFolder;
    private String ImagePath;
    private int ImageWidth = 500,ImageHeight = 600;  //Default Values

    public ImageModel(String path) throws ImageAccessException {
        Path = path;
        URL DirURL = ImageModel.class.getResource(Path);
        ImagePath = DirURL.getPath().substring(1);
        try {
            ImgFolder = new File(DirURL.toURI());
            initImageList();
        } catch (URISyntaxException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        }
    }

    private void initImageList() {
        ImageList = new LinkedList<String>();
        String Images[] = ImgFolder.list();
        ImageList.addAll(Arrays.asList(Images));
    }

    public List<String> getImageList() {
        return ImageList;
    }

    public Image getImage(String img) throws ImageAccessException {
        Image CurrentImage = null;
        try (FileInputStream fis = new FileInputStream(ImagePath+ img)) {
            CurrentImage = ImageIO.read(fis);
        } catch (FileNotFoundException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        } catch (IOException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        }
        return CurrentImage;
    }
    
  public static String getImagePath(){
      return Path;
  }
    //Not Used
    public Image resizeImage(Image image) {
        image = image.getScaledInstance(ImageWidth, ImageHeight, Image.SCALE_DEFAULT);
        image.flush();
        return image;
    }

}
