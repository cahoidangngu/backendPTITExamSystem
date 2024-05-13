package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.mappers.QuizzMapper;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RoleRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {
    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAXIMUM_PAGE_SIZE = 500;
    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizzRepository quizzRepository;
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
    public Page<User> getAllUser() throws UserNotFoundException{
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) throw new UserNotFoundException("No users in list");
        return (Page<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) throw new UserNotFoundException("Not found user");
        return optionalUser.get();
    }

    @Override
    public List<QuizzDTO> getQuizzesByUserId(Long userId) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isEmpty())
        {
            throw new UserNotFoundException("User with id " + userId + " not found!");
        }
        List<Quizz> quizzes = optional.get().getRegisterQuizzes()
                .stream().map(RegisterQuizz::getQuizz)
                .collect(Collectors.toList());

        return QuizzMapper.MAPPER.toResponses(quizzes);
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
        if(page == null) page = DEFAULT_PAGE;
        if(pageSize == null) pageSize = DEFAULT_PAGE_SIZE;
        if(field != null) return userRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field)));
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }
}
