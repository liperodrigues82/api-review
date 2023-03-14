package com.review.domain.dtos;

import com.review.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private Integer age;

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        password = user.getPassword();
        age = user.getAge();
    }

    public User fromDTO() {
        return new User(id, name, password, age);
    }

}
