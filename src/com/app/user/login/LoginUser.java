/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.user.security.SecurityExReason;
import com.app.user.security.SecurityException;
import com.app.user.security.ValidationModel;
import java.util.ArrayList;
import java.util.Collection;
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
public class LoginUser {

    private String Username;
    private Map<String, Integer> P2Password;
    private final String split_password_regex = "[&|]";
    private List<String> OtherImages;                            //List of Images other than those that are present in Users P2Password.
    private Iterator<Map.Entry<String, Integer>> P2Iterator;     // Iterate through users p2password.
    private Iterator<String> OtherImgIterator;                   // Iterate Through other image list
    private InnerResult Status;                                 // Maintains Inner State of LoginUser , Not Displayed to User
    private Map.Entry<String, Integer> CurrentEntry;           //Current Image + Grid Combination from P2Password that is being authenticated.
    private int imageindex = 0;                               //Helps in determining position of P2Iteraotr.

    public LoginUser(String username, String p2password, List<String> imglist) throws SecurityException {
        Username = username;
        P2Password = new LinkedHashMap<>();
        initPhase2(p2password);
        initOtherImages(imglist);
        P2Iterator = P2Password.entrySet().iterator();
        Status = InnerResult.ENTER;
    }

    private void initPhase2(String password) throws SecurityException {
        if (ValidationModel.validateP2Passowrd(password)) {       // checks if P2Passowrd retrieved from DB is valid or not else throw exception
            Scanner sc = new Scanner(password);
            sc.useDelimiter(split_password_regex);               // Use to Split the Password into Images and GridNumbers.
            while (sc.hasNext()) {
                String ImageName = sc.next();
                int GridNo = sc.nextInt();
                P2Password.put(ImageName, GridNo);             // Add Entry into p2password
            }
        } else {
            throw new SecurityException(SecurityExReason.PASS_REGEX_CHECK_ERROR, "Password retireved from database doesnt pass the regex check!");
        }
    }

    //Takes the Image List having all Images i.e OtherImages , remove all Images that are present in P2Password List.
    private void initOtherImages(List<String> ImageList) {
        OtherImages = new ArrayList<>(ImageList);
        OtherImages.removeAll(P2Password.keySet());
        Collections.shuffle(OtherImages);
        OtherImgIterator = OtherImages.iterator();

    }

    public LoginStatus authenticateGrid(int gridno) {
        LoginStatus result = null;

        switch (Status) {
            case ENTER:
                //Retrieving 1st Image , ignore input param.
                if (P2Iterator.hasNext()) {
                    Status = InnerResult.VALID;
                    result = LoginStatus.CONTINUE;
                    CurrentEntry = P2Iterator.next();        //Make 1st Image + Grid No Combn as CurrentEntry to be Authenticated.
                    result.setMessage(CurrentEntry.getKey());
                } else {
                    Status = InnerResult.EXIT;
                    result = LoginStatus.ERROR;
                }
                break;

             //For 1st Image , Input is checked , if match is found continue with next entry in P2Iterator and send CONTINUE Status including next image name.
             // if there is no next entry in P2Iterator send SUCCESS Status.
            case VALID:
                if (gridno == CurrentEntry.getValue()) {
                    if (P2Iterator.hasNext()) {
                        imageindex++;
                        result = LoginStatus.CONTINUE;
                        CurrentEntry = P2Iterator.next();
                        result.setMessage(CurrentEntry.getKey());
                    } else {
                        result = LoginStatus.SUCCESS;
                        Status = InnerResult.EXIT;
                    }
                    break;
                }
                Status = InnerResult.INVALID;

             //If grid no input != current entry grid numbers , change inner state to INVALID send continue message to user with next Image from OtherImageIteator to display wrong images.    
             //limit of 5 images set 
            case INVALID:
                if (imageindex < 4 && OtherImgIterator.hasNext()) {
                    result = LoginStatus.CONTINUE;
                    result.setMessage(OtherImgIterator.next());
                    imageindex++;
                } else {
                    Status = InnerResult.EXIT;
                    result = LoginStatus.FAILURE;
                }
                break;

            case EXIT:
                result = LoginStatus.ERROR;
                         destroy();
                break;

        }
        return result;
    }

    private void destroy() {
        Username = null;
        P2Password = null;
        OtherImages = null;
        P2Iterator = null;
        CurrentEntry = null;
        imageindex = -1;
    }

}
