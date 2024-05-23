package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.RoleDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;
import com.quanchun.backendexamsystem.models.responses.QuizDTO;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.QuizzService;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAXIMUM_PAGE_SIZE = 500;
    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizzService quizzService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDTO toUserDTO(User user) {
        RoleDTO roleDTO = new RoleDTO(user.getRole().getRoleId(), user.getRole().getName());
        return UserDTO.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .userId(user.getUserId())
                .dob(user.getDob())
                .role(roleDTO)
                .gender(user.getGender())
                .imagePath(user.getImagePath())
                .password(user.getPassword())
                .studyClass(user.getStudyClass())
                .build();
    }

    @Override
    public User toUser(UserDTO userDTO, boolean status) {

        Role role = new Role();
        role.setRoleId(userDTO.getRole().getId());
        role.setName(userDTO.getRole().getName());
        if (status)
            return User.builder()
                    .username(userDTO.getUsername())
                    .fullName(userDTO.getFullName())
                    .phone(userDTO.getPhone())
                    .address(userDTO.getAddress())
                    .dob(userDTO.getDob())
                    .gender(userDTO.getGender())
                    .imagePath(userDTO.getImagePath())
                    .password(userDTO.getPassword())
                    .studyClass(userDTO.getStudyClass())
                    .role(role)
                    .build();
        return User.builder().username(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .userId(userDTO.getUserId())
                .dob(userDTO.getDob())
                .gender(userDTO.getGender())
                .imagePath(userDTO.getImagePath())
                .password(userDTO.getPassword())
                .studyClass(userDTO.getStudyClass())
                .role(role)
                .build();
    }

    @Override
    public User addNewUser(UserDTO newUser) throws RoleNotFoundException {
        Role role = roleService.getRoleById(newUser.getRole().getId());
        User user = toUser(newUser, true);
        user.setRole(role);
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUser() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new UserNotFoundException("No users in list");
        return users;
    }

    @Override
    public List<UserDTO> getAllStudent() throws UserNotFoundException, RoleNotFoundException {
        Role userRole = roleService.getRoleById(2);
        List<UserDTO> studentList = new ArrayList<>();
        userRepository.findByRole(userRole).forEach(user -> {
            studentList.add(toUserDTO(user));
        });
        return studentList;
    }


    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        return optionalUser.get();
    }

    @Override
    public List<QuizDTO> getQuizzesByUserId(Long userId) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found!");
        }

        return optional.get().getRegisterQuizzes()
                .stream().map(registerQuizz -> {
                    return quizzService.toQuizDTO(registerQuizz.getQuizz());
                })
                .toList();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
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
        if (Objects.nonNull(userLogin.getUsername()) && Objects.nonNull(userLogin.getPassword())) {
            Optional<User> optionalUser = userRepository.findByUsername(userLogin.getUsername());
            if (!optionalUser.isPresent())
                throw new UserNotFoundException("username not found");
            return optionalUser.get().getPassword().equals(userLogin.getPassword());
        }
        return false;
    }

    @Override
    public User deleteUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        userRepository.deleteById(userId);
        return optionalUser.get();
    }

    @Override
    public List<User> getUserWithSorting(String field, Sort.Direction direction) {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));

    }


    @Override
    public Page<User> getUserWithPagination(Integer offset, Integer page) {
        return userRepository.findAll(PageRequest.of(offset, page));
    }

    @Override
    public Page<User> getUserWithSortAndPagination(String field, Integer page, Integer pageSize) {
        if (page == null) page = DEFAULT_PAGE;
        if (pageSize == null) pageSize = DEFAULT_PAGE_SIZE;
        if (field != null) return userRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field)));
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }
}
