package ru.newbank.zakupki.info_getter.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "purchase_info")
public class PurchaseInfo {

    private Timestamp updated_at;
    @Id
    private long purchase_number;
    private int notice_id;

}
