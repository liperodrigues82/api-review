package com.review.services;

import com.review.domain.dtos.UserDTO;
import com.review.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    public UserDTO insertUser(UserDTO dto) {
        var obj = repository.save(dto.fromDTO());
        return new UserDTO(obj);
    }

    public List<UserDTO> getAllUsers() {
        var list = repository.findAll();
        return list.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        var obj = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
        return new UserDTO(obj);
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        var obj = repository.findById(id)
                .map(u -> {
                    u.setName(dto.getName());
                    u.setPassword(dto.getPassword());
                    u.setAge(dto.getAge());
                    return repository.save(u);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
        return new UserDTO(obj);
    }

    public void deleteUser(Long id) {
        var obj = getUserById(id);
        repository.delete(obj.fromDTO());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User
                .builder()
                .username("felipe")
                .password(encoder.encode("12345"))
                .roles("ADMIN")
                .build();
    }
}
