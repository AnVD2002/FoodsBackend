package com.example.Food.Service.User;

import com.example.Food.Entity.User.CustomUserDetails;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.FindByName(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        else {
            return new CustomUserDetails(user.get());
        }
    }
}
