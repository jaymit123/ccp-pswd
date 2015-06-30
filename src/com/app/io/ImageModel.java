/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.io;

import java.awt.Image;
import com.app.exceptions.ImageAccessException;
import java.util.List;
import java.util.LinkedList;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 *
 * @author VJ
 */
public class ImageModel {

    private String Path;
    private List<String> ImageList;
    private URL ImageDirURL;
    private File ImgFolder;

    public ImageModel(String path) throws ImageAccessException {
        Path = path;
        ImageDirURL = ImageModel.class.getResource(Path);
        try {
            ImgFolder = new File(ImageDirURL.toURI());
            initImageList();
        } catch (URISyntaxException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        }
    }

    private void initImageList() {
        ImageList = new LinkedList<>();
        String Images[] = ImgFolder.list();
        for (String images : Images) {
            ImageList.add(images);
        }
    }

    public List<String> getImageList() {
        return ImageList;
    }

    public Image getImage(String img) throws ImageAccessException {
        Image CurrentImage = null;
        try (FileInputStream fis = new FileInputStream(Path + img)) {
            CurrentImage = resizeImage(ImageIO.read(fis));
        } catch (FileNotFoundException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        } catch (IOException ex) {
            throw new ImageAccessException("An Error Occured while accessing the requested image.\nPlease restart the software or contact me at jaymit_123@hotmail.com", ex);
        }
        return CurrentImage;
    }

    public Image resizeImage(Image image) {
        image = image.getScaledInstance(600, 500, Image.SCALE_DEFAULT);
        image.flush();
        return image;
    }

}
