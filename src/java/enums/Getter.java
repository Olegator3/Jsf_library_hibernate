/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author ПК
 */
public enum Getter {
    
    IMAGE("image"),PDF("content");
    String name;
    Getter(String arg){
        name = arg;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
