/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.user.register.RegisterStatus;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 *
 * @author VJ
 */
public class RegisterUser {

    private String Username;
    private String P1Password;
    private Map<String, Integer> P2Password;
    private int IteratorIndex = 0;

    public RegisterUser(String username, String p1password) {
        Username = username;
        P1Password = p1password;
        P2Password = new LinkedHashMap<String, Integer>();
    }

    public RegisterStatus addEntry(String image, int GridNo) {
        RegisterStatus Status;
        if (IteratorIndex < 5) {
            P2Password.put(image, GridNo);
            Status = RegisterStatus.ADDED;
            if (IteratorIndex == 2) {
                Status = RegisterStatus.ALERT;
            } else if (IteratorIndex == 4) {
                Status = RegisterStatus.REGISTER_SUCCESS;
            }
            ++IteratorIndex;
        } else {
            Status = RegisterStatus.REGISTER_FAILED;
        }
        return Status;
    }
    


    public String[] getUserRecord() {
        String[] record = {Username, P1Password, convertPassword()};
        destroy();
        return record;
    }

    private String convertPassword() {
        String result = null;
        if (P2Password != null) {
            StringBuilder Data = new StringBuilder();
            for (Map.Entry addEntry : P2Password.entrySet()) {
                Data.append("|" + addEntry.getKey() + "&" + addEntry.getValue());
            }
            result = Data.toString();
        }
        return result;
    }

    private void destroy() {
        Username = null;
        P1Password = null;
        P2Password = null;
    }

    public void resetPhase2() {
        if (Username != null) {
            P2Password = new LinkedHashMap<String, Integer>();
            IteratorIndex = 0;
        }
    }

}
