package ru.newbank.zakupki.info_getter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;

public interface InfoRepository extends CrudRepository<PurchaseInfo, Integer> {



}
