package com.spring_security_project.service;

import com.spring_security_project.model.AppUser;
import com.spring_security_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public AppUser registerUser(AppUser appUser){
        return userRepository.save(appUser);
    }

    //Read:
    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }

    //Read by id:
    public AppUser getUserById(Long id){
        Optional<AppUser> appUser = userRepository.findById(id);
        return appUser.orElse(null);
    }

    //Update by id:
    public AppUser updateUserById(AppUser appUser){
        if(userRepository.existsById(appUser.getId())){
            return userRepository.save(appUser);
        } else {
            return null;
        }
    }

    //Delete by id:
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
