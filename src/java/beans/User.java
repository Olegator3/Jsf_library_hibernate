/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@SessionScoped
public class User implements Serializable{

    private String name;
    private String password;
    private boolean entered = false;
    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
      public String login(){
       HttpServletRequest hs = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            hs.login(name, password);
            entered = true;
            return "books";
        } catch (ServletException ex) {
            ResourceBundle rb = ResourceBundle.getBundle("properties/lang",FacesContext.getCurrentInstance().getViewRoot().getLocale());
           
            FacesMessage ms = new FacesMessage(rb.getString("error_auth"));
            ms.setSeverity(ms.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("login_form", ms);
        }
       return "";
   }
   
   public String exit(){
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
            entered = false;
        } catch (ServletException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
       return "exit";
   }

    public boolean isEntered() {
        return entered;
    }
   
   
}
