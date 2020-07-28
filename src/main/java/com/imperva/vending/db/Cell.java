package com.imperva.vending.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cells")
public class Cell {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "cell_code")
    private String cellCode;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "quantity")
    private Integer quantity;

    @Override
    public String toString() {
        return "Cell{" +
                "id=" + id +
                ", cellCode='" + cellCode + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}


