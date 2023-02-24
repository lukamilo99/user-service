package raf.sk.userservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message){
        super(message);
    }
}

