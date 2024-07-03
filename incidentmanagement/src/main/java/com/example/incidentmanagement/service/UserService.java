package com.example.incidentmanagement.service;
import com.example.incidentmanagement.dto.UserDTO;
import com.example.incidentmanagement.entities.User;
import com.example.incidentmanagement.entities.enums.UserRole;
import com.example.incidentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
//public class UserService implements UserDetailsService {
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setAddress(userDTO.getAddress());
        newUser.setPinCode(userDTO.getPinCode());
        newUser.setCity(userDTO.getCity());
        newUser.setCountry(userDTO.getCountry());
        newUser.setRoles(Collections.singleton(UserRole.USER)); // Default role
        return userRepository.save(newUser);
    }

    public User updateUser(User updatedUser) {
        // Ensure the user exists
        Optional<User> existingUserOptional = userRepository.findById(updatedUser.getUserId());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update user details based on what's allowed to be updated
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPinCode(updatedUser.getPinCode());
            existingUser.setCity(updatedUser.getCity());
            existingUser.setCountry(updatedUser.getCountry());

            return userRepository.save(existingUser);
        }

        throw new IllegalArgumentException("User with ID " + updatedUser.getUserId() + " not found.");
    }
    public void deleteUser(Long userId) {
        // Ensure the user exists
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            userRepository.delete(existingUserOptional.get());
        } else {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.name()))
//                .collect(Collectors.toList());
//    }
}
