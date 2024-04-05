package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User addNewUser(UserDTO newUser) throws RoleNotFoundException {
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
        user.addRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() throws UserNotFoundException{
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) throw new UserNotFoundException("No users in list");
        return users;
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        return optionalUser.get();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        return optionalUser.get();
    }

    @Override
    public User updateUserById(Long userId, UserDTO updateUser) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new UserNotFoundException("Not user found");
        User user = optionalUser.get();
        if (Objects.nonNull(updateUser.getUsername())) {
            user.setUsername(updateUser.getUsername());
        }
        if (Objects.nonNull(updateUser.getFullName())) {
            user.setFullName(updateUser.getFullName());
        }
        if (Objects.nonNull(updateUser.getPassword())) {
            user.setPassword(updateUser.getPassword());
        }
        if (Objects.nonNull(updateUser.getDob().compareTo(user.getDob()))) {
            user.setDob(updateUser.getDob());
        }
        if (Objects.nonNull(updateUser.getStudyClass())) {
            user.setStudyClass(updateUser.getStudyClass());
        }
        if (Objects.nonNull(updateUser.getAddress())) {
            user.setAddress(updateUser.getAddress());
        }
        if (Objects.nonNull(updateUser.getImagePath())) {
            user.setImagePath(updateUser.getImagePath());
        }
        user.setGender(updateUser.getGender());
        return userRepository.save(user);
    }

    @Override
    public boolean userLogin(UserLoginDTO userLogin) throws UserNotFoundException {
        if (Objects.nonNull(userLogin.getUsername()) && Objects.nonNull( userLogin.getPassword())){
            Optional<User> optionalUser = userRepository.findByUsername(userLogin.getUsername());
            if(!optionalUser.isPresent())
                throw new UserNotFoundException("username not found");
            return  optionalUser.get().getPassword().equals(userLogin.getPassword());
        }
        return false;
    }

    @Override
    public User deleteUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        userRepository.deleteById(userId);
        return optionalUser.get();
    }
}
