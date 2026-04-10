package com.kishor.Ecommerce.project.utils;

import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;
    public String loggedInEmail(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByUserName(authentication.getName())
                .orElseThrow(()->new UsernameNotFoundException("user not found execptions"));
        return user.getEmail();
    }
    public Long loggedInUserId(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByUserName(authentication.getName())
                .orElseThrow(()->new UsernameNotFoundException("user not Found execptions"));
        return user.getUserId();
    }
public User loggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    User user = userRepository.findByUserName(authentication.getName())  // ✅ fixed here
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return user;
}

}

