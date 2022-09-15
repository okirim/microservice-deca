package com.zema.app.role;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "roles")
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique= true, nullable = false, length = 50)
    private String name;

    @Column(unique= true, nullable = false, length = 255)
    private String description;
}
