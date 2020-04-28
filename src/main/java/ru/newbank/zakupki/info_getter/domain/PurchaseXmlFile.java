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
    int xmlId;

    @NonNull
    @Column(name = "purchase_number")
    long purchaseNumber;

    @NonNull
    @Column(name = "file_name")
    String fileName;

    @Column(name = "xml_file")
    @NonNull
    String xmlFile;
}
