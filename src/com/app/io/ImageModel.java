/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author VJ
 */
public class ImageModel {

    private List<String> imagesList;
    private List<String> thumbnailsList;
    private String thumbnailsPath;
    private String imagesPath;
    private int imageWidth = 500, imageHeight = 600;  //Default Values

    public ImageModel(String path) throws ImageAccessException {
        imagesPath = path + "/Images/";
        thumbnailsPath = path + "/Thumbnails/";
        initImagesList(new File(imagesPath));
        initThumbnailsList(new File(thumbnailsPath));
        compareImageList();
    }

    private void initImagesList(File iml) throws ImageAccessException {
        imagesList = new LinkedList<>();
        String Images[] = iml.list(); 
         if(Images == null) throw new ImageAccessException("No Images Found.\n Please check Images Folder");      
        imagesList.addAll(Arrays.asList(Images));
    }

    private void initThumbnailsList(File tml) throws ImageAccessException {
        thumbnailsList = new LinkedList<>();
        String Images[] = tml.list();
       if(Images == null) throw new ImageAccessException("No Images Found.\n Please check Thumbnails Folder");
        thumbnailsList.addAll(Arrays.asList(Images));
    }

    private void compareImageList() throws ImageAccessException {
        if (imagesList.retainAll(thumbnailsList)) {
            throw new ImageAccessException("No Images Found.\n Please check both folders.");
        }else if(imagesList.size() < 10){
            throw new ImageAccessException("Minimum 10 images required.\n Please check both folders.");
        }
    }

    public List<String> getImageList() {
        return imagesList;
    }

    public BufferedImage getImage(String img) throws ImageAccessException {
        BufferedImage CurrentImage = null;
        try (FileInputStream fis = new FileInputStream(imagesPath + img)) {
            CurrentImage = ImageIO.read(fis);
        } catch (FileNotFoundException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        } catch (IOException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        }
        return CurrentImage;
    }

    //Not Used
    public Image resizeImage(Image image) {
        image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
        image.flush();
        return image;
    }

}
