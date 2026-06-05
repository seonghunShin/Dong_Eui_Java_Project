package org.yourcompany.yourproject.dto;

import lombok.Data;

@Data
public class LoginResDto {
    private String userId;
    private String name;
    private String role;
}