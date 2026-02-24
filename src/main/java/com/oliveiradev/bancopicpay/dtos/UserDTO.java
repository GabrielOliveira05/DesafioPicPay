package com.oliveiradev.bancopicpay.dtos;

import com.oliveiradev.bancopicpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
