package com.auth_service.entity;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //NotNull
    private String nome;
    //NotNull
    private String email;
    //NotNull
    private String senha;
    //NotNull
    @Enumerated(EnumType.STRING)
    private Enum role;

    //NotNull
    private Boolean ativo;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


}
