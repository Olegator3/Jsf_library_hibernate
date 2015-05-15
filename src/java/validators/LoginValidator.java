/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validators/LoginValidator")
public class LoginValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       String login = value.toString();
       ResourceBundle bd = ResourceBundle.getBundle("properties/lang",FacesContext.getCurrentInstance().getViewRoot().getLocale());
       FacesMessage error = null;
       try{
           int no = Integer.parseInt(login.substring(0,1));
           error = new FacesMessage(bd.getString("login_st_error"));
           error.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(error);
       }catch(NumberFormatException e){
           
           }
           if(login.length() < 4){
                error = new FacesMessage(bd.getString("login_short_error"));
           error.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(error);
           }
       }
        
    }
    

