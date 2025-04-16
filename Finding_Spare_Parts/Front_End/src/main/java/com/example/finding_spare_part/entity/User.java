package com.example.finding_spare_part.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "userid", updatable = false, nullable = false)
    private UUID userid;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String role;

    // Pre-persist method to ensure UUID is created before saving
    public void ensureId() {
        if (this.userid == null) {
            this.userid = UUID.randomUUID();
        }
    }
}