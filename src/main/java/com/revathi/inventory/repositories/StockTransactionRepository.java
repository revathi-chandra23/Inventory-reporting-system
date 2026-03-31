package com.revathi.inventory.repositories;

import com.revathi.inventory.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    @Query("SELECT COALESCE(SUM(CASE WHEN st.type = 'IN' THEN st.quantity ELSE -st.quantity END), 0) " +
            "FROM StockTransaction st WHERE st.product.id = :productId")
    int findTotalStockByProductId(@Param("productId") Long productId);

    List<StockTransaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
}
