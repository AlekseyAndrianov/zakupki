package ru.newbank.zakupki.info_getter.domain;

import lombok.*;

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
    private int xmlId;

    @NonNull
    @Column(name = "purchase_number")
    private long purchaseNumber;

    @NonNull
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "xml_file", columnDefinition = "TEXT")
    @NonNull
    private String xmlFile;
}
