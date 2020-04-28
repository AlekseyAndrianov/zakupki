package ru.newbank.zakupki.info_getter.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;

import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
@Transactional
public interface InfoRepository extends CrudRepository<PurchaseInfo, Long> {


//    @Query("select pi from PurchaseInfo pi where pi.purchase_number = :p_number")
//    List<PurchaseInfo> findByPurchase_number(@Param("p_number") long purchase_number);

//    List<PurchaseInfo> findByPurchaseNumber(long purchaseNumber);

//    PurchaseInfo findFirstByPurchaseNumber(long purchaseNumber);
}
