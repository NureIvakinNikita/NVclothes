package com.example.nvclothes.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "client", schema = "project")
@Builder
@AllArgsConstructor
@Access(AccessType.FIELD)
public class ClientEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Name isn't assigned properly it must contain letters from a to z and A to Z")
    private String name;

    @Column(name = "lastname")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name isn't assigned properly it must contain letters from a to z and A to Z")
    private String lastName;


    @Column
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[\\w_.+-]*[\\w_.]@(?:[\\w]+\\.)+[a-zA-Z]{2,7}$", message = "Email isn't assigned properly")
    private String email;

    @Column
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(name = "telephone_number")
    @NotBlank(message = "Telephone number cannot be empty")
    private String telephoneNumber;

    @Column
    @Nullable
    private Date birthday;

    @Column
    @Builder.Default
    private boolean enabled = false;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cart_id;



    @OneToMany(mappedBy = "client")
    private List<OrderClientEntity> orders;

   /* @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> role = new HashSet<>();*/

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }



}
