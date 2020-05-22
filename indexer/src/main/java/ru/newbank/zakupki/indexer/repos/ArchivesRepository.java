package ru.newbank.zakupki.indexer.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;

@Repository
@EnableTransactionManagement
@Transactional
public interface ArchivesRepository extends CrudRepository<ArchivesForRegion, Integer> {

    ArchivesForRegion findByArchiveName(String archiveName);


}
