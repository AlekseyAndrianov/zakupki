package ru.newbank.zakupki.info_getter.domain;

import lombok.Data;
import org.json.XML;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.SQLXML;

@Data
@Entity
@Table(name = "purchase_xml_file")
public class PurchaseXmlFile {

    @Id
    @Column(name = "xml_id")
    int xmlId;

    @NonNull
    @Column(name = "purchase_number")
    long purchaseNumber;

    @Column(name = "xml_file")
    String xmlFile;
}