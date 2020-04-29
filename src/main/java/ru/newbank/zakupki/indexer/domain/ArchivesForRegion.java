package ru.newbank.zakupki.indexer.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "archives_for_region")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ArchivesForRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int archive_id;

    @Column(name = "archive_name")
    @NonNull String archiveName;

    @NonNull String region;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
