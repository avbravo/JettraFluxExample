/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flux.plugin.example.model;

import io.jettra.flux.annotations.JettraViewModel;
import io.jettra.flux.annotations.PropertiesLabel;
import io.jettra.rules.validations.Email;
import io.jettra.rules.validations.Min;
import io.jettra.rules.validations.NotNull;

/**
 *
 * @author avbravo
 */
@JettraViewModel
public class PersonModel {

    @NotNull
    @PropertiesLabel(value = "person.name", label = "Nombre")
    public String name = "name";
    @Email
    @PropertiesLabel(value = "person.email", label = "Email")
    public String email = "email";
    @Min(value = 0)
    @PropertiesLabel(value = "person.age", label = "Edad")
    public Integer age = 0;

    public PersonModel() {
    }

    public PersonModel(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
