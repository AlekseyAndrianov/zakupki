package ru.newbank.zakupki.info_getter.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;

@Repository
@EnableTransactionManagement
@Transactional
public interface InfoRepository extends CrudRepository<PurchaseInfo, Long> {

}
