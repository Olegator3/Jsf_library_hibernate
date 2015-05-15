/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import db.DBWorker;
import entity.HibernateUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;


public class HibernateFilter implements Filter{
        SessionFactory sf;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         sf = HibernateUtil.getSessionFactory();
         
    }

        @Override
    public void doFilter(ServletRequest request,  
                         ServletResponse response,  
                         FilterChain chain)  
            throws IOException, ServletException {  
  
        try {  
            
            sf.getCurrentSession().beginTransaction();  
                
           
            chain.doFilter(request, response);  
  
            
           
            sf.getCurrentSession().getTransaction().commit();  
  
        } catch (StaleObjectStateException staleEx) {  
           
            throw staleEx;  
        } catch (Throwable ex) {  
            // Rollback only  
            ex.printStackTrace();  
            try {  
                if (sf.getCurrentSession().getTransaction().isActive()) {  
                    
                    sf.getCurrentSession().getTransaction().rollback();  
                }  
            } catch (Throwable rbEx) {  
               
            }  
  
            // Let others handle it... maybe another interceptor for exceptions?  
            throw new ServletException(ex);  
        }  
    }  

    @Override
    public void destroy() {
      
    }
    
}
