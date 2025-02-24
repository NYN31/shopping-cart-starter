//package com.bazlur.shoppingcart.utility.filter;
//
//import com.bazlur.shoppingcart.utility.security.SecurityContext;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.logging.Filter;
//import java.util.logging.LogRecord;
//import java.util.stream.Stream;
//
//@WebFilter(urlPatterns = "/*")
//public class AuthenticationFilter implements Filter {
//    private static final String[] ALLOWED_CONTENTS
//            = {".css", ".js", ".jpg", ".JPG", ".png", "home", "login", "signup"};
//
//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response,
//                         FilterChain chain)
//        throws IOException, ServletException{
//        var httpServletRequest = (HttpServletRequest) request;
//        var requestedUri = httpServletRequest.getRequestURI();
//
//        boolean allowed = Stream.of(ALLOWED_CONTENTS)
//                .anyMatch(requestedUri::contains);
//        if(requestedUri.equals("/") || allowed || SecurityContext.isAuthenticated(httpServletRequest)){
//            chain.doFilter(request, response);
//        }else {
//            ((HttpServletResponse) response).sendRedirect("/login");
//        }
//    }
//
//    @Override
//    public boolean isLoggable(LogRecord record) {
//        return false;
//    }
//}
