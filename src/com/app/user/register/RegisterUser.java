/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import java.util.Map;
import java.util.LinkedHashMap;
import com.app.user.security.AESCipher;
import com.app.user.security.AESCipherException;

/**
 *
 * @author VJ
 */
public class RegisterUser {

    private String username;
    private String p1Password;
    private Map<String, Integer> p2Password;
    private int iteratorIndex = 0;

    public RegisterUser(String uname, String p1password) {
        username = uname;
        p1Password = p1password;
        p2Password = new LinkedHashMap<String, Integer>();
    }

    public RegisterStatus addEntry(String image, int GridNo) {
        RegisterStatus Status;
        if (iteratorIndex < 5) {
            p2Password.put(image, GridNo);
            Status = RegisterStatus.ADDED;
            if (iteratorIndex == 2) {
                Status = RegisterStatus.ALERT;
            } else if (iteratorIndex == 4) {
                Status = RegisterStatus.REGISTER_SUCCESS;
            }
            ++iteratorIndex;
        } else {
            Status = RegisterStatus.REGISTER_FAILED;
        }
        return Status;
    }

    public String[] getUserRecord() throws AESCipherException {
        String record[] = null;
        String encryptedPswd = new AESCipher().encryptPassword(username, p1Password, convertPassword());
        record = new String[]{username, encryptedPswd};
        destroy();

        return record;
    }

    private String convertPassword() {
        String result = null;
        if (p2Password != null) {
            StringBuilder Data = new StringBuilder();
            for (Map.Entry addEntry : p2Password.entrySet()) {
                Data.append("|" + addEntry.getKey() + "&" + addEntry.getValue());
            }
            result = Data.toString();
        }
        return result;
    }

    private void destroy() {
        username = null;
        p1Password = null;
        p2Password = null;
    }

    public void resetPhase2() {
        if (username != null) {
            p2Password = new LinkedHashMap<String, Integer>();
            iteratorIndex = 0;
        }
    }

}
