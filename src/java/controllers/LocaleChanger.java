/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable {
   private  Locale currentLocale = new Locale("ru");
   public LocaleChanger(){
   }
 

    public Locale getCurrentLocale() {
        return currentLocale;
    }
    public void changeLocale(String code){
        currentLocale = new Locale(code);
    }
    
}
