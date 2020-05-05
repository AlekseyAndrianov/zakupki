package ru.newbank.zakupki.info_getter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "purchase_xml_file")
@NoArgsConstructor
@RequiredArgsConstructor
public class PurchaseXmlFile {

    @Id
    @Column(name = "xml_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer xmlId;

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
