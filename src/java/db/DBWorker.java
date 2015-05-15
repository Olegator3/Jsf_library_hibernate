
package db;

import entity.Author;
import entity.Book;
import entity.Genre;
import entity.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DBWorker {
   private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
   private static DBWorker instance = null;
   private static List<Author> authorList;

   private DBWorker(){
        sessionFactory.getCurrentSession().beginTransaction();
         authorList = (ArrayList<Author>)sessionFactory.getCurrentSession()
                    .createCriteria(Author.class).list();
          sessionFactory.getCurrentSession().getTransaction().commit();
   }

    public static DBWorker getInstance() {
        if(instance == null){
            instance = new DBWorker();
        }
        return instance;
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
      
    public List<Book> getAllBooks(int from,int to){  
        List<Book> result = sessionFactory.getCurrentSession().createCriteria(Book.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .addOrder(Order.asc("name"))
                .list();  
        return result;
    }
    public List<Genre> getAllGenries(){   
        sessionFactory.getCurrentSession().beginTransaction();
        List<Genre> result = sessionFactory.getCurrentSession().createCriteria(Genre.class).list();     
        sessionFactory.getCurrentSession().getTransaction().commit();
        return result;
    }
    
    public List<Book> getBooksByGenre(Long id,int from,int to){ 
        List<Book> result = sessionFactory.getCurrentSession().createCriteria(Book.class)
                .add(Restrictions.eq("genre.id", id))
                .setFirstResult(from)
                .setMaxResults(to)
                .addOrder(Order.asc("name"))
                .list();
       
        return result;
    }
      public Book getConcreteBook(Long id){   
        Book result = (Book) sessionFactory.getCurrentSession().createCriteria(Book.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return result;  
    }
   public List<Book> getBooksByName(String name,int from,int to){  
       List<Book> result = getBookByParam("name",name,MatchMode.ANYWHERE,from,to);
     return result;
   }
    public List<Book> getBooksByLetter(String letter,int from,int to){       
       List<Book> result = getBookByParam("name",letter,MatchMode.START,from,to);
       return result;
   }
   public List<Book> getBooksByAuthor(String authorName,int from,int to){ 
    
      Long authorId = 0l;
       for(Author author :authorList)
       {
           if(author.getFio().toLowerCase().contains(authorName.toLowerCase())){
               authorId = author.getId();
               break;
           }
       }
       List<Book> result = sessionFactory.getCurrentSession()
               .createCriteria(Book.class)
               .add(Restrictions.eq("author.id", authorId))
               .setFirstResult(from)
               .setMaxResults(to)
               .addOrder(Order.asc("name"))
               .list();
    
       return result;
   }
    private List<Book> getBookByParam(String param, String text,MatchMode mode,int from,int to) {
        List<Book> result = sessionFactory.getCurrentSession().createCriteria(Book.class)
                .add(Restrictions.ilike(param, text, mode))
                 .setFirstResult(from)
                .setMaxResults(to)
                .addOrder(Order.asc("name"))
                .list();
        return result;
    }
    public byte[] getSomeContent(String contentName,Long id){ 
        byte[] result = (byte[]) sessionFactory.getCurrentSession().createCriteria(Book.class)
                .setProjection(Projections.property(contentName))
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        return result;
    }

    //work with pages:counting of books
    public long getAllBooksCount() {
        return (long) sessionFactory.getCurrentSession()
                .createCriteria(Book.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    public long getGenreBooksCount(Long id) {
        return (long) sessionFactory.getCurrentSession().createCriteria(Book.class)
                .add(Restrictions.eq("genre.id", id))
                .setProjection(Projections.rowCount()).uniqueResult();
    }
    
     public long getBooksByNameCount(String name){  
       return getBookByParamCount("name",name,MatchMode.ANYWHERE);
  
   }
      public long getBooksByLetterCount(String letter){       
      return getBookByParamCount("name",letter,MatchMode.START);
      
   }
     
    private long getBookByParamCount(String param, String text, MatchMode mode) {
       return (long) sessionFactory.getCurrentSession().createCriteria(Book.class)
                .add(Restrictions.ilike(param, text, mode)).setProjection(Projections.rowCount()).uniqueResult();
    }

    public long getBooksByAuthorCount(String searchString) {
         Long authorId = 0l;
       for(Author author :authorList)
       {
           if(author.getFio().toLowerCase().contains(searchString.toLowerCase())){
               authorId = author.getId();
               break;
           }
       }
        return (long) sessionFactory.getCurrentSession()
               .createCriteria(Book.class)
               .add(Restrictions.eq("author.id", authorId))
               .setProjection(Projections.rowCount()).uniqueResult();
    }
    
}
