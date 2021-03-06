/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.user.security.AuthenticationExReason;
import com.app.user.security.AuthenticationException;
import com.app.user.security.ValidationModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author VJ
 */
public class LoginUser {

    private String userName;
    private Map<String, Integer> p2Password;
    private final String split_Password_Regex = "[&|]";
    private List<String> otherImages;                            //List of Images other than those that are present in Users P2Password.
    private Iterator<Map.Entry<String, Integer>> p2Iterator;     // Iterate through users p2password.
    private Iterator<String> otherImgIterator;                   // Iterate Through other image list
    private InnerStatus Status;                                 // Maintains Inner State of LoginUser , Not Displayed to User
    private Map.Entry<String, Integer> currentEntry;           //Current Image + Grid Combination from P2Password that is being authenticated.
    private int imageIndex = 0;                               //Helps in determining position of P2Iteraotr.

    public LoginUser(String uname, String p2password, List<String> imglist) throws AuthenticationException {
        userName = uname;
        p2Password = new LinkedHashMap<>();
        initPhase2(p2password);
        initOtherImages(imglist);
        p2Iterator = p2Password.entrySet().iterator();
        Status = InnerStatus.ENTER;
    }

    private void initPhase2(String password) throws AuthenticationException {
        if (ValidationModel.validateP2Passowrd(password)) {       // checks if P2Passowrd retrieved from DB is valid or not else throw exception
            Scanner sc = new Scanner(password);
            sc.useDelimiter(split_Password_Regex);               // Use to Split the Password into Images and GridNumbers.
            while (sc.hasNext()) {
                String ImageName = sc.next();
                int GridNo = sc.nextInt();
                p2Password.put(ImageName, GridNo);             // Add Entry into p2password
            }
        } else {
            throw new AuthenticationException(AuthenticationExReason.PASS_REGEX_CHECK_ERROR, "Password retireved from database doesnt pass the regex check!");
        }
    }

    //Takes the Image List having all Images i.e OtherImages , remove all Images that are present in P2Password List.
    private void initOtherImages(List<String> ImageList) throws AuthenticationException {
        Set<String> p2Set = p2Password.keySet();
        otherImages = new ArrayList<>(ImageList);
        if (!otherImages.containsAll(p2Set)) {
            throw new AuthenticationException(AuthenticationExReason.ACC_IMG_NOT_FOUND, "Sorry Images are missing!");
        }
        otherImages.removeAll(p2Set);
        Collections.shuffle(otherImages);
        otherImgIterator = otherImages.iterator();

    }

    public LoginStatus authenticateGrid(int gridno) {
        LoginStatus result = null;

        switch (Status) {
            case ENTER:
                //Retrieving 1st Image , ignore input param.
                if (p2Iterator.hasNext()) {
                    Status = InnerStatus.VALID;
                    result = LoginStatus.INIT;
                    currentEntry = p2Iterator.next();        //Make 1st Image + Grid No Combn as CurrentEntry to be Authenticated.
                    result.setMessage(currentEntry.getKey());
                } else {
                    Status = InnerStatus.EXIT;
                    result = LoginStatus.ERROR;
                }
                break;

            //For 1st Image , Input is checked , if match is found continue with next entry in P2Iterator and send CONTINUE Status including next image name.
            // if there is no next entry in P2Iterator send SUCCESS Status.
            case VALID:
                if (gridno == currentEntry.getValue()) {
                    if (p2Iterator.hasNext()) {
                        imageIndex++;
                        result = LoginStatus.CONTINUE;
                        currentEntry = p2Iterator.next();
                        result.setMessage(currentEntry.getKey());
                    } else {
                        result = LoginStatus.SUCCESS;
                        Status = InnerStatus.EXIT;
                    }
                    break;
                }
                Status = InnerStatus.INVALID;

            //If grid no input != current entry grid numbers , change inner state to INVALID send continue message to user with next Image from OtherImageIteator to display wrong images.    
            //limit of 5 images set 
            case INVALID:
                if (imageIndex < 4 && otherImgIterator.hasNext()) {
                    result = LoginStatus.CONTINUE;
                    result.setMessage(otherImgIterator.next());
                    imageIndex++;
                } else {
                    Status = InnerStatus.EXIT;
                    result = LoginStatus.FAILURE;
                    destroy();
                }
                break;

            case EXIT:
                result = LoginStatus.ERROR;
                break;

        }
        return result;
    }

    private void destroy() {
        userName = null;
        p2Password = null;
        otherImages = null;
        p2Iterator = null;
        currentEntry = null;
        imageIndex = -1;
    }

}
