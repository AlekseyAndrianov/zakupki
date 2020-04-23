package ru.newbank.zakupki.info_getter.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "purchase_info")
public class PurchaseInfo {

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Id
    @Column(name = "purchase_number")
    private Long purchaseNumber;

    @Column(name = "notice_id")
    private int noticeId;

}
