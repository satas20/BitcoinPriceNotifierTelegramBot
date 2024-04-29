package com.example.telegrambotapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@RequiredArgsConstructor
@Getter
@Setter

public class Chat {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private int threshold;
    private long notifyPrice;
    private boolean notifyWhenPriceHigher;
}
