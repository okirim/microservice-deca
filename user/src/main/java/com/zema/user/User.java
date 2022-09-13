package com.zema.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
//@PasswordMatch
public class User implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean emailVerificationStatus;

    @Column(nullable = true)
    private String emailVerificationToken;

    @Column(nullable = false, length = 100)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(nullable = true)
    private String passwordResetToken;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean passwordResetStatus;

    private Date createdAt;

    @Column(nullable = true)
    private Date updatedAt;
//    @ElementCollection
//    @CollectionTable(name = "user_addresses", joinColumns = @JoinColumn(name = "user_id"))
//    @AttributeOverrides({
//            @AttributeOverride(name = "addressLine1",column =@Column(name = "address_house_number")),
//            @AttributeOverride(name = "addressLine2",column =@Column(name = "street"))
//    })
//    private Set<Address> addresses = new HashSet<>();

//    @ManyToOne(targetEntity = RoleEntity.class)
//    private RoleEntity role;




    @PrePersist
    private void beforePersist() {
        this.createdAt = new Date();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password= bCryptPasswordEncoder.encode(this.password);
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Date(System.currentTimeMillis());
    }
}