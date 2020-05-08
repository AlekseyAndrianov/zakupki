package ru.newbank.zakupki.indexer.service;

import lombok.Getter;

@Getter
public enum Region {
    MAGADANSKAYA("Magadanskaja");
    private String name;

    Region(String name) {
        this.name = name;
    }
}
