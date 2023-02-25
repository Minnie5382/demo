package com.example.demo.src.menu.model;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOptionRes {
    private int optionId;
    private String optionGroup;
    private String optionName;
    private int optionPrice;

}
