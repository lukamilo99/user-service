package raf.sk.userservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RankNotFoundException extends RuntimeException{

    private String message;
}
