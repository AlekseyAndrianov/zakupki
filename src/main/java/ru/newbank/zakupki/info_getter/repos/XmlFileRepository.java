package ru.newbank.zakupki.info_getter.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;

import java.util.List;

@Repository
public interface XmlFileRepository extends CrudRepository<PurchaseXmlFile, Integer> {


    //List<PurchaseXmlFile> findAllXmlById(long purchase_number);



}