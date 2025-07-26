package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;
    private String ip;
    private String action;
    @Column(name = "resource")
    private String resource;

    @Lob
    @Column(name = "data_out", columnDefinition = "LONGTEXT")
    private String dataOut;

    @Lob
    @Column(name = "data_in", columnDefinition = "LONGTEXT")
    private String dataIn;
    private String level;

}
