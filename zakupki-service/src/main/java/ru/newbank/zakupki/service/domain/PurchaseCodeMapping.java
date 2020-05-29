package ru.newbank.zakupki.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "purchase_code_mapping")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseCodeMapping {

    @Id
    @Column(name = "purchase_code")
    private String purchaseCode;

    @Column(name = "purchase_number")
    private Long purchaseNumber;

}
