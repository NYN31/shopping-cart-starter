package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.dto.UserDTO;
import com.bazlur.shoppingcart.repository.UserRepository;
import com.bazlur.shoppingcart.service.UserService;
import com.bazlur.shoppingcart.service.impl.UserServiceImpl;
import com.bazlur.shoppingcart.utility.validation.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private UserService userService
            = new UserServiceImpl(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
        throws ServletException, IOException{

        req.getRequestDispatcher(
                "/WEB-INF/signup.jsp"
        ).forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
        throws ServletException, IOException{

        UserDTO userDTO = copyParametersTo(req);
        var errors = ValidationUtil.getInstance().validate(userDTO);

        if(!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, res);
        }else if(userService.isNotUniqueUsername(userDTO)){
            errors.put("username", "The username already exists.");
            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, res);
        }else if(userService.isNotUniqueEmail(userDTO)){
            errors.put("username", "The email already exists.");
            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, res);
        }else {
            userService.saveUser(userDTO);
            res.sendRedirect("/login");
        }
    }

    private UserDTO copyParametersTo(HttpServletRequest req){
        var userDTO = new UserDTO();
        userDTO.setFirstName(req.getParameter("firstName"));
        userDTO.setLastName(req.getParameter("lastName"));
        userDTO.setUsername(req.getParameter("username"));
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setPasswordConfirmed(req.getParameter("passwordConfirmed"));

        return userDTO;
    }
}
