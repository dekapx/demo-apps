package net.dekapx.demoapp.batch.writer;

import lombok.extern.slf4j.Slf4j;
import net.dekapx.demoapp.domain.TradeHistory;
import net.dekapx.demoapp.domain.TradeTransaction;
import net.dekapx.demoapp.service.TradeHistoryService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component("tradeItemWriter")
public class TradeItemWriter implements ItemWriter<TradeTransaction> {
    private TradeHistoryService tradeHistoryService;

    @Autowired
    public TradeItemWriter(final TradeHistoryService tradeHistoryService) {
        this.tradeHistoryService = tradeHistoryService;
    }

    @Override
    public void write(final List<? extends TradeTransaction> transactions) {
        final List<TradeHistory> tradeHistories = transactions
                .stream()
                .map(toTradeHistory)
                .collect(Collectors.toList());

        tradeHistories.forEach(tradeHistory -> this.tradeHistoryService.create(tradeHistory));
    }

    private Function<TradeTransaction, TradeHistory> toTradeHistory = (transaction) -> {
        final TradeHistory history = new TradeHistory();
        history.setTradeId(transaction.getTradeId());
        history.setTradeName(transaction.getTradeName());
        history.setPrice(transaction.getPrice());
        history.setTransactionDate(transaction.getTransactionDate());
        return history;
    };
}
