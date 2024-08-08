package com.learning.springboot11jpahibernetasociaciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client_details")
public class ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private boolean premium;
    private int points;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Client client;

    @Override
    public String toString() {
        return "ClientDetails{" +
                "Id=" + Id +
                ", premium=" + premium +
                ", points=" + points +
                '}';
    }
}
