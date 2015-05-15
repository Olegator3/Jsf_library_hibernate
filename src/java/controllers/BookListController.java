/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import db.DBWorker;
import entity.Book;
import enums.Getter;
import enums.SearchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.criterion.Projections;


@ManagedBean
@SessionScoped
public class BookListController implements Serializable {
    private ArrayList<Book> currentBooks = new ArrayList();
    private ArrayList<Integer>pages = new ArrayList();
  
    private String searchString;
    private SearchType searchType;
    private static ArrayList<Character> russianLetters = new ArrayList(Arrays.asList('А','Б','В','Г','Д',
    'Е','Ё','Ж','З','И',
    'Й','К','Л','М','Н',
    'О','П','Р','С','Т',
    'У','Ф','Х','Ц','Ч',
    'Ш','Ь','Щ','Ъ','Э',
    'Ю','Я'));
    private String selectedGenre = "-1";
    private String selectedLetter = "-1";
    private long countOfBooks = 0;
    private int MaxBooksOnPage = 6;
    private int countOfPages;
    private int selectedPage = 0;
    private String currentSql = "";
    
  

     public BookListController() {
     
        fillBooksAll(false);
    }

    public long getCountOfBooks() {
      
        return countOfBooks;
    }

      public int getSelectedPage() {
        return selectedPage;
    }
 
    public ArrayList<Integer> getPages() {
        
        return pages;
    }
     
    public void setSelectedGenre(String selectedGenre) {
        this.selectedGenre = selectedGenre;
    }

    public void setSelectedLetter(String selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public String getSelectedGenre() {
        return selectedGenre;
    }

    public String getSelectedLetter() {
        return selectedLetter;
    }

     
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }
     
    public ArrayList<Character> getRussianLetters() {
  
        return russianLetters;
    }


    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }
  
  
      public void selectPage(){
          String page = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("page");
        selectedPage = Integer.valueOf(page);
        
        switch(currentSql){
            case "all":fillBooksAll(true);break;
            case "genre":fillBookGenre(true);break;
            case "search":fillBooksBySearch(true);break;
            case "letter":fillBooksByLetter(true);break;
        }
       
    }
    public String fillBooksAll(boolean isPage){
        redirect = false;
       
        if(!isPage){
         resetParam();
        countOfBooks = DBWorker.getInstance().getAllBooksCount();
        countOfPages();
        }
       currentBooks = (ArrayList<Book>) DBWorker.getInstance().getAllBooks(selectedPage*MaxBooksOnPage,MaxBooksOnPage);
      
      currentSql = "all";
     return "books";
    }
   
      public String fillBookGenre(boolean isPage){
          redirect = false;
         
          Long id;
          try{
          id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_genre"));
          }catch(Exception e){
              id = Long.parseLong(selectedGenre);
          }
          if(!isPage){
              resetParam();
              countOfBooks = DBWorker.getInstance().getGenreBooksCount(id);
              countOfPages();
          }
          
          selectedGenre = id.toString();
          currentBooks = (ArrayList<Book>) DBWorker.getInstance().getBooksByGenre(id,selectedPage*MaxBooksOnPage,MaxBooksOnPage);
          currentSql = "genre";
          return "books";
    }
      public String fillBooksBySearch(boolean isPage){
          redirect = false;
        
          if(searchType == SearchType.AUTHOR){
                
              if(!isPage){
                  resetParam();
                  countOfBooks = DBWorker.getInstance().getBooksByAuthorCount(searchString);
                  countOfPages();
              }
              currentBooks =  (ArrayList<Book>) DBWorker.getInstance().getBooksByAuthor(searchString,selectedPage*MaxBooksOnPage,MaxBooksOnPage);

          }else{
               if(!isPage){
                   resetParam();
                  countOfBooks = DBWorker.getInstance().getBooksByNameCount(searchString);
                  countOfPages();
              }
              currentBooks =  (ArrayList<Book>) DBWorker.getInstance().getBooksByName(searchString,selectedPage*MaxBooksOnPage,MaxBooksOnPage);
          }
          currentSql="search";
          return "books";
      }
      public String fillBooksByLetter(boolean isPage){
          redirect = false;
          
          Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
          String letter = params.get("letter");
          if(letter == null)
              letter = selectedLetter;
          if(!isPage){
              resetParam();
              countOfBooks = DBWorker.getInstance().getBooksByLetterCount(letter);
              countOfPages();
          }
          selectedLetter = letter;
         currentBooks = (ArrayList<Book>) DBWorker.getInstance().getBooksByLetter(letter,selectedPage*MaxBooksOnPage,MaxBooksOnPage);
         currentSql = "pages";
         return "books";
      }
      
    
      private void countOfPages(){
          countOfPages =  (int)Math.ceil((double)countOfBooks/MaxBooksOnPage);
          for(int i =1;i<=countOfPages;i++){
              pages.add(i);
          }
          
      }
      private void resetParam(){
          selectedGenre="-1";
         selectedLetter = "-1";
        
         currentBooks.clear();
         pages.clear();
         
      }
     public byte[] getSomeContent(String id,Getter getter){
       if(getter == Getter.IMAGE){
           return DBWorker.getInstance().getSomeContent("image", Long.parseLong(id));
       }else return DBWorker.getInstance().getSomeContent("content", Long.parseLong(id));
     }
  public void loading(){
       try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    private boolean redirect = false;
    public String changeRedirect(){
        redirect = !redirect;
           return "info";
        
    }
   
    public boolean isRedirect() {
        return redirect;
    }
    
}
