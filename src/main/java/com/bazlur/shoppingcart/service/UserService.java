package com.bazlur.shoppingcart.service;

import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.dto.LoginDTO;
import com.bazlur.shoppingcart.dto.UserDTO;
import com.bazlur.shoppingcart.exception.UserNotFoundException;

public interface UserService {
    void saveUser(UserDTO userDTO);
    boolean isNotUniqueUsername(UserDTO user);
    boolean isNotUniqueEmail(UserDTO user);
    User verifyUser(LoginDTO loginDTO) throws UserNotFoundException;
}
