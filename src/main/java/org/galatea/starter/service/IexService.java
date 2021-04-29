package org.galatea.starter.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexService {

  @NonNull
  private IexClient iexClient;
  @NonNull
  private IexStockClient iexStockClient;


  /**
   * Get all stock symbols from IEX.
   *
   * @return a list of all Stock Symbols from IEX.
   */
  public List<IexSymbol> getAllSymbols() {
    return iexClient.getAllSymbols();
  }

  /**
   * Get the last traded price for each Symbol that is passed in.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @return a list of last traded price objects for each Symbol that is passed in.
   */
  public List<IexLastTradedPrice> getLastTradedPriceForSymbols(final List<String> symbols) {
    if (CollectionUtils.isEmpty(symbols)) {
      return Collections.emptyList();
    } else {
      return iexClient.getLastTradedPriceForSymbols(symbols.toArray(new String[0]));
    }
  }

  /**
   * Get the historical prices for the symbol passed in during a specific time range.
   *
   * @param symbol the symbol to get historical prices price for.
   * @param range the period of time to get historical prices for. The default is 1m (one month).
   *              It can also be a specific date.
   * @param chartByDay Used only when range is date to return OHLCV data instead of minute bar data.
   * @param token the API key used to hit the iex historical prices endpoint
   * @return a list of IexHistoricalPrices objects for the given symbol and time range
   */
  public List<IexHistoricalPrice> getHistoricalPricesForSymbol(final String symbol, final String range,
      final String chartByDay, final String token) {
    if (range == null) {
      return iexStockClient.getHistoricalPricesForSymbol(symbol, token);
    } else {
      return iexStockClient.getHistoricalPricesForSymbolWithRange(symbol, range, chartByDay, token);
    }

  }

}
