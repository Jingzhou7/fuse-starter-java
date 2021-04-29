package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEXStock", url = "${spring.rest.iexStockBasePath}")
public interface IexStockClient {

  /**
   * Get the historical prices for the symbol passed in.
   *
   * @param symbol the symbol to get historical prices price for.
   * @param token the API key used to hit the iex historical prices endpoint
   * @return a list of IexHistoricalPrices objects for the given symbol and time range
   */
  @GetMapping("/stock/{symbol}/chart")
  List<IexHistoricalPrice> getHistoricalPricesForSymbol(@PathVariable(value="symbol") String symbol,
      @RequestParam(value="token") String token);

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
  @GetMapping("/stock/{symbol}/chart/{range}")
  List<IexHistoricalPrice> getHistoricalPricesForSymbolWithRange(@PathVariable(value="symbol") String symbol,
      @PathVariable(value="range") String range,
      @RequestParam(value="chartByDay") String chartByDay,
      @RequestParam(value="token") String token);

}
