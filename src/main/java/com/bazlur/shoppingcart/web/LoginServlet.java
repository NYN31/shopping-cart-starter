package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.dto.LoginDTO;
import com.bazlur.shoppingcart.dto.UserDTO;
import com.bazlur.shoppingcart.exception.UserNotFoundException;
import com.bazlur.shoppingcart.repository.UserRepository;
import com.bazlur.shoppingcart.service.UserService;
import com.bazlur.shoppingcart.service.impl.UserServiceImpl;
import com.bazlur.shoppingcart.utility.security.SecurityContext;
import com.bazlur.shoppingcart.utility.validation.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
        throws ServletException, IOException{
        String logout = req.getParameter("logout");
        if(logout != null && Boolean.parseBoolean(logout)){
            req.setAttribute("message", "You have been successfully logged out.");
        }
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
        throws ServletException, IOException{

        var loginDTO = new LoginDTO(req.getParameter("username"),
                                    req.getParameter("password"));

        var errors = ValidationUtil.getInstance().validate(loginDTO);
        if(!errors.isEmpty()){
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
        }

        try{
            login(loginDTO, req);
            res.sendRedirect("/home");
        }catch(UserNotFoundException e){
            errors.put("username", "Incorrect username or password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest req) throws UserNotFoundException{
        User user = userService.verifyUser(loginDTO);

        SecurityContext.login(req, user);
    }

}
