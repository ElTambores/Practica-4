package com.servidor.Practica4.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long _id;

    String title;

    //slug es el titulo pero en minusculas y sustituir espacios por _
    //tiene que ser único (el titulo no). Si ya existe uno meter número random al final hasta que no lo sea.
    String slug;

    //Siempre
    int __v;

    //Ejemplo color "hsl(11, 50%, 50%)"
    String color;

    String description;

    //Por ahora estará vacio.
    List<User> moderators;

}
