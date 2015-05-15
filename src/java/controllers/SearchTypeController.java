/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.SearchType;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SearchTypeController {
     private  Map<String, SearchType> searchParams = new HashMap();

    public SearchTypeController() {
        ResourceBundle bundle = ResourceBundle.getBundle("properties/lang",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchParams.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchParams.put(bundle.getString("tittle_book"), SearchType.TITLE);
    }

    public Map<String, SearchType> getSearchParams() {
        return searchParams;
    }
     
     
}
