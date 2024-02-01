package com.inventory.system.exotic0.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    @Id
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean state;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "editor")
    private List<Receipt> receipts;
}
