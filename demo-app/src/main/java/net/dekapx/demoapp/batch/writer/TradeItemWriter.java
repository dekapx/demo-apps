package net.dekapx.demoapp.batch.writer;

import lombok.extern.slf4j.Slf4j;
import net.dekapx.demoapp.domain.TradeTransaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("tradeItemWriter")
public class TradeItemWriter implements ItemWriter<TradeTransaction> {
    @Override
    public void write(List<? extends TradeTransaction> list) throws Exception {

    }
}
