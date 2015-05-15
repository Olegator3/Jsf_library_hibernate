/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import db.DBWorker;
import entity.Book;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class EditController implements Serializable{
      String currentId;
       private boolean editmode = false;
       private Book concreteBook;
     

    public EditController() {
        
         setCurrentId();
    }
    public void initBook(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         BookListController bc = (BookListController) request.getSession().getAttribute("bookListController");
        
         concreteBook = DBWorker.getInstance().getConcreteBook(Long.parseLong(currentId));
         if(!bc.isRedirect()){
       bc.changeRedirect();
            try {
              
                FacesContext.getCurrentInstance().getExternalContext().redirect(request.getRequestURI()+"?id_book="+currentId);
               
            } catch (Exception ex) {
                Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         
         
         
    }
    public Book getConcreteBook() {
        return concreteBook;
    }
    
       
       public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId() {
        currentId =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_book");
        
        initBook();
       
    }

    
//    public void updateBook(){
//        String sql = "update book set name=?, page_count=?, isbn=?, publish_year=?, description=?, genre_id=? where id=?";
//      
//       GenreController gc = (GenreController) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("genreController");
//       BookListController bc = (BookListController) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("bookListController");
//        Book book = concreteBook;
//         try(Connection cn = DBWorker.getConnection();
//                 PreparedStatement st = cn.prepareStatement(sql)){
//             st.setString(1, book.getName());
//             st.setInt(2, book.getPageCount());
//             st.setString(3, book.getIsbn());
//             st.setInt(4,book.getPublishYear());
//             st.setString(5, book.getDescription());
//             try{
//             st.setInt(6, Integer.valueOf(book.getGenre()));
//             }catch(NumberFormatException e)
//             {
//             st.setInt(6,gc.getGenreMap().get(book.getGenre()));
//             }
//             st.setInt(7, book.getId());
//             
//             st.addBatch();
//             
//             st.executeBatch();
//             
//         }catch(SQLException e){
//             
//         }
//       
//         book.setGenre(gc.getGenreforId(book.getGenre()));
//         swtchEditMode();
//    }
//    
//   

    public boolean isEditmode() {
        return editmode;
        
    }
    public void swtchEditMode(){
        editmode=!editmode;
    }


   
}
