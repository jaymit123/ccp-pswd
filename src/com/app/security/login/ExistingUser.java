/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security.login;
import com.app.exceptions.SecurityExReason;
import com.app.exceptions.SecurityException;
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

    private String Username;
    private Map<String, Integer> P2Password;
    private final String split_password_regex = "[&|]";
    private final String check_password_regex = "(\\|[^/?<>\\\\:*|]+\\.[a-zA-Z0-9]{3,5}\\&\\d{2}){3,5}";
    private List<String> OtherImages;
    private Iterator<Map.Entry<String, Integer>> P2Iterator;
    private Iterator<String> OtherImgIterator;
    private InnerResult Status;
    private Map.Entry<String, Integer> CurrentEntry;
    private int imageindex = 0;                         //Helps in determining position of P2Iteraotr.

    public ExistingUser(String username, String p2password, List<String> imglist) throws SecurityException {
        Username = username;
        P2Password = new LinkedHashMap<>();
        initPhase2(p2password);
        initOtherImages(imglist);
        P2Iterator = P2Password.entrySet().iterator();
        Status = InnerResult.ENTER;
    }

    private void initPhase2(String password)throws SecurityException {
        if(password.matches(check_password_regex)){
             Scanner sc = new Scanner(password);
        sc.useDelimiter(split_password_regex);
        while (sc.hasNext()) {
            String ImageName = sc.next();
            int GridNo = sc.nextInt();
            P2Password.put(ImageName, GridNo);
        }}else{
            throw new SecurityException(SecurityExReason.PASS_REGEX_CHECK_ERROR,"Password retireved from database doesnt pass the regex check!");
        }
    }

    private void initOtherImages(List ImageList) {
        OtherImages = new ArrayList<>(ImageList);
        OtherImages.removeAll(P2Password.keySet());
        Collections.shuffle(OtherImages);
        OtherImgIterator = OtherImages.iterator();

    }

    public PasswordResult authenticateGrid(int gridno) {
        PasswordResult result = null;

        switch (Status) {
            case ENTER:

                if (P2Iterator.hasNext()) {
                    Status = InnerResult.VALID;
                    result = PasswordResult.CONTINUE;
                    CurrentEntry = P2Iterator.next();
                    result.setMessage(CurrentEntry.getKey());
                } else {
                    Status = InnerResult.EXIT;
                    result = PasswordResult.ERROR;
                }
                break;

            case VALID:
                if (gridno == CurrentEntry.getValue()) {
                    if (P2Iterator.hasNext()) {
                        imageindex++;
                        result = PasswordResult.CONTINUE;
                        CurrentEntry = P2Iterator.next();
                        result.setMessage(CurrentEntry.getKey());
                    } else {
                        result = PasswordResult.SUCCESS;
                        Status = InnerResult.EXIT;
                    }
                    break;
                }
                Status = InnerResult.INVALID;

            case INVALID:
                if (imageindex < 4 && OtherImgIterator.hasNext()) {
                    result = PasswordResult.CONTINUE;
                    result.setMessage(OtherImgIterator.next());
                    imageindex++;
                } else {
                    Status = InnerResult.EXIT;
                    result = PasswordResult.FAILURE;
                    destroy();
                }
                break;

            case EXIT:
                result = PasswordResult.ERROR;
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
