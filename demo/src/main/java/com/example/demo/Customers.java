package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Customers")
public class Customers {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY )
private Long id;
private String name;
@Column(name="userId", unique=true)
private String userId;
@Column(name="email", unique=true)
private String email;
@Column(name="cardNumber", unique=true)
private Long cardNumber;
private int creditScore;
}
