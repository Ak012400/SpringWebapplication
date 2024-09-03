package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="Students")
public class Students {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Long id;
public String Name;
public String Email;
public String Age;   
}
