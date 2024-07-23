package ru.stepup.prodservise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
