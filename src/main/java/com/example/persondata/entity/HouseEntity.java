package com.example.persondata.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "houses", uniqueConstraints = @UniqueConstraint(columnNames = {"street", "number"})) //
@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "street")
    private String street;

    @Column(name = "number")
    @Positive
    private Integer number;

}