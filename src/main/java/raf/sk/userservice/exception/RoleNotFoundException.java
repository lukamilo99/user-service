package raf.sk.userservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleNotFoundException extends RuntimeException{
    private String message;
}
