package net.dekapx.demoapp.repository;

import net.dekapx.demoapp.domain.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    TradeTransaction findByTradeId(String tradeId);
}
