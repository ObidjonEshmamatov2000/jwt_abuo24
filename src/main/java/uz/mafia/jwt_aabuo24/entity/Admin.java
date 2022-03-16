package uz.mafia.jwt_aabuo24.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Admin extends User{
    private String social;
}
