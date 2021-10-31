package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.utility.security.SecurityContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                         HttpServletResponse res) throws IOException{
        SecurityContext.logout(req);
        res.sendRedirect("/login?logout=true");
    }
}
