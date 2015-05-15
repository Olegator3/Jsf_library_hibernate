/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import db.DBWorker;
import entity.Genre;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class GenreController {
    private ArrayList<Genre> list = new ArrayList();
    private Map<String,Long> genreMap = new LinkedHashMap();
    
    
    
    public GenreController() {
        getGenreList();
        for(Genre genre: list){
            genreMap.put(genre.getName(), genre.getId());
        }
    }

    public Map<String, Long> getGenreMap() {
        return genreMap;
    }
    
   
    private void getGenreList(){
       list = (ArrayList<Genre>) DBWorker.getInstance().getAllGenries();

    }
    public ArrayList<Genre> getList() {
        
        return list;
    }
    
    public  String getGenreforId(String id){
        String result = null;
        for(Genre genre : list){
            if(genre.getId().toString().equals(id)){
                result = genre.getName();
                break;
            }
        }
    
        return result;
    }
}