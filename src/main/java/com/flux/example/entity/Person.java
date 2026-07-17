/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.flux.example.entity;

import io.jettra.rules.validations.Min;
import io.jettra.rules.validations.NotNull;

/**
 *
 * @author avbravo
 */
public record Person(@NotNull String name, String email,@Min(value = 0) Integer age) {

}
