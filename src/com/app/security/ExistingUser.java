/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author VJ
 */
public class ExistingUser {

    private final String Username;
    private Map<String, Integer> P2Password;
    private final String regex = "[&|]";
    private List<String> OtherImages;
    private Iterator<Map.Entry<String, Integer>> P2Iterator;
    private Iterator<String> OtherImgIterator;
    private InnerResult Status = InnerResult.VALID;
    private Map.Entry<String, Integer> CurrentEntry;

    public ExistingUser(String username, String p2password, List<String> imglist) {
        Username = username;
        P2Password = new LinkedHashMap<>();
        initPhase2(p2password);
        initOtherImages(imglist);
        P2Iterator = P2Password.entrySet().iterator();
    }

    private void initPhase2(String password) {
        Scanner sc = new Scanner(password);
        sc.useDelimiter(regex);
        while (sc.hasNext()) {
            String ImageName = sc.next();
            int GridNo = sc.nextInt();
            P2Password.put(ImageName, GridNo);
        }
        System.out.println(P2Password.toString());
    }

    private void initOtherImages(List ImageList) {
        OtherImages = new ArrayList<>(ImageList);
        OtherImages.removeAll(P2Password.keySet());
        Collections.shuffle(OtherImages);
        OtherImgIterator = OtherImages.iterator();

    }

    public String getNextImage() {
        String ans = null;
        switch (Status) {
            case VALID:
                CurrentEntry = P2Iterator.next();               //store the reference to next copy Entry containing Image and Associated GridNumber.
                ans = CurrentEntry.getKey();
                break;

            case INVALID:
                ans = OtherImgIterator.next();
                break;

            case EXIT:
                break;

        }
        return ans;
    }

    public PasswordResult authenticateGrid(String img, int gridno) {
        PasswordResult result = null;
        if (img == null && gridno == -1) {   //When the Object will be created, passing these values 
            result = result.CONTINUE;
            if (P2Iterator.hasNext()) {
                CurrentEntry = P2Iterator.next();
                result.setImage(null);    CurrentEntry.getKey();  //Get Image and store in result
            }
        } else {
if(img)
        }
        return result;
    }
}
