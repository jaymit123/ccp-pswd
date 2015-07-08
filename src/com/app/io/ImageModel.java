/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.io;

import java.awt.Image;
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

    private List<String> ImagesList;
    private List<String> ThumbnailsList;
    private String ThumbnailsPath;
    private String ImagesPath;
    private int ImageWidth = 500, ImageHeight = 600;  //Default Values

    public ImageModel(String path) throws ImageAccessException {
        ImagesPath = path + "/Images/";
        ThumbnailsPath = path + "/Thumbnailsd/";
        initImagesList(new File(ImagesPath));
        initThumbnailsList(new File(ThumbnailsPath));
        compareImageList();
    }

    private void initImagesList(File iml) throws ImageAccessException {
        ImagesList = new LinkedList<>();
        String Images[] = iml.list(); 
         if(Images == null) throw new ImageAccessException("No Images Found.\n Please check Images Folder");      
        ImagesList.addAll(Arrays.asList(Images));
    }

    private void initThumbnailsList(File tml) throws ImageAccessException {
        ThumbnailsList = new LinkedList<>();
        String Images[] = tml.list();
       if(Images == null) throw new ImageAccessException("No Images Found.\n Please check Thumbnails Folder");
        ThumbnailsList.addAll(Arrays.asList(Images));
    }

    private void compareImageList() throws ImageAccessException {
        if (ImagesList.retainAll(ThumbnailsList)) {
            throw new ImageAccessException("No Images Found.\n Please check both folders.");
        }
    }

    public List<String> getImageList() {
        return ImagesList;
    }

    public Image getImage(String img) throws ImageAccessException {
        Image CurrentImage = null;
        try (FileInputStream fis = new FileInputStream(ImagesPath + img)) {
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
        image = image.getScaledInstance(ImageWidth, ImageHeight, Image.SCALE_DEFAULT);
        image.flush();
        return image;
    }

}
