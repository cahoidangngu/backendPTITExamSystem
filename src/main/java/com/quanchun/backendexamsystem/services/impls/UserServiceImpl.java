package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;
import com.quanchun.backendexamsystem.repositories.RoleRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User addNewUser(UserDTO newUser) {
        User user = User.builder()
                .username(newUser.getUsername())
                .fullName(newUser.getFullName())
                .password(newUser.getPassword())
                .dob(newUser.getDob())
                .studyClass(newUser.getStudyClass())
                .gender(newUser.getGender())
                .address(newUser.getAddress())
                .imagePath(newUser.getImagePath())
                .build();
        Role role = roleService.getRoleById(newUser.getRoleId());
        if (role==null) return null;
        user.addRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUserById(Long userId, UserDTO updateUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return null;
        User user = optionalUser.get();
        if (updateUser.getUsername() != null) {
            user.setUsername(updateUser.getUsername());
        }
        if (updateUser.getFullName()!= null) {
            user.setFullName(updateUser.getFullName());
        }
        if (updateUser.getPassword()!= null) {
            user.setPassword(updateUser.getPassword());
        }
        if (updateUser.getDob().compareTo(user.getDob())!=0) {
            user.setDob(updateUser.getDob());
        }
        if (updateUser.getStudyClass()!= null) {
            user.setStudyClass(updateUser.getStudyClass());
        }
        if (updateUser.getAddress()!= null) {
            user.setAddress(updateUser.getAddress());
        }
        if (updateUser.getImagePath()!= null) {
            user.setImagePath(updateUser.getImagePath());
        }
        user.setGender(updateUser.getGender());
        return userRepository.save(user);
    }

    @Override
    public boolean userLogin(UserLoginDTO userLogin) {
        if (userLogin.getUsername()!= null && userLogin.getPassword()!= null){
            User user = userRepository.findByUsername(userLogin.getUsername());
            return  user.getPassword().equals(userLogin.getPassword());
        }
        return false;
    }
}
