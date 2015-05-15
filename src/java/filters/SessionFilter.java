/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import beans.User;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "SessionFilter", urlPatterns = "/index.xhtml")
public class SessionFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestH = (HttpServletRequest)request;
        HttpServletResponse responeH = (HttpServletResponse)response;
        
        User user = (User)requestH.getSession().getAttribute("user");
        
        if(user!=null && user.isEntered()){
          
           responeH.sendRedirect(requestH.getContextPath()+"/pages/book.xhtml");
           
        }
        else{
            chain.doFilter(request, response);
           
        }
        
       
    }

    @Override
    public void destroy() {
    
    }
    
}
