/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security.register;

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

    public EntryStatus addEntry(String image, int GridNo) {
        EntryStatus Status;
        if (IteratorIndex < 5) {
            P2Password.put(image, GridNo);
            Status = EntryStatus.ADDED;
            if (IteratorIndex == 2) {
                Status = EntryStatus.ALERT;
            } else if (IteratorIndex == 4) {
                Status = EntryStatus.COMPLETED;
            }
            ++IteratorIndex;
        }else  Status = EntryStatus.ERROR;
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
