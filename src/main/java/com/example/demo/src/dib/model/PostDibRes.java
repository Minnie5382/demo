package com.example.demo.src.dib.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostDibRes {
    private int userId;
    private int shopId;
    private int dibId;
}
