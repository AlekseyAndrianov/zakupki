package ru.newbank.zakupki.indexer.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "purchase_info")
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class PurchaseInfo {

    @Id
    @Column(name = "purchase_number")
    @NonNull
    private Long purchaseNumber;

    @Column(name = "notice_id")
    @NonNull
    private Integer noticeId;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;
}
