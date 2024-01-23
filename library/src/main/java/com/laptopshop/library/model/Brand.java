package com.laptopshop.library.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;
    private String name;
    @Column(name = "is_activated")
    private boolean activated;
    @Column(name = "is_deleted")
    private boolean deleted;
}
