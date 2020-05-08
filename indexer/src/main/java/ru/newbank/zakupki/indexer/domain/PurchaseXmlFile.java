package ru.newbank.zakupki.indexer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "purchase_xml_file")
@NoArgsConstructor
@RequiredArgsConstructor
public class PurchaseXmlFile {

    @Id
    @NonNull
    @Column(name = "purchase_number")
    private Long purchaseNumber;

    @NonNull
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "xml_file", columnDefinition = "TEXT")
    @NonNull
    private String xmlFile;
}
