package ru.newbank.zakupki.info_getter.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;

@Repository
@EnableTransactionManagement
@Transactional
public interface XmlFileRepository extends CrudRepository<PurchaseXmlFile, Integer> {

    PurchaseXmlFile findByPurchaseNumber(Long purchaseNumber);

}