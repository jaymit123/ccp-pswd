/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

/**
 *
 * @author VJ
 */
public enum InnerResult {

   ENTER,           //When LoginUser Object is created first time it is in ENTER State.
   
   VALID,          // If User selects correct grid for corresponding image than it enters VALID State.
   
   INVALID,       // if User selects wrong grid than it enters INVALID State and Starts sending wrong images to user , it can't go back to VALID State.
   
   EXIT;         // Once All Combinations of Grid + Images are checked LoginUser Object enters EXIT State.
  
}
