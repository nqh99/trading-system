package org.aqua.trading.component.factory;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientFactory {
  private static HttpComponentsClientHttpRequestFactory defaultRequestFactory;

  public RestClientFactory(HttpComponentsClientHttpRequestFactory requestFactory) {
    RestClientFactory.defaultRequestFactory = requestFactory;
  }

  public RestClient createRestClient() {
    return RestClient.builder().requestFactory(defaultRequestFactory).build();
  }

  public RestClient createRestClient(
      int connectionTimeoutInMs, int readTimeoutInMs, int connectionRequestTimeoutInMs) {
    return RestClient.builder()
        .requestFactory(
            createRequestFactory(
                connectionTimeoutInMs, readTimeoutInMs, connectionRequestTimeoutInMs))
        .build();
  }

  private HttpComponentsClientHttpRequestFactory createRequestFactory(
      int connectionTimeoutInMs, int readTimeoutInMs, int connectionRequestTimeoutInMs) {
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(connectionTimeoutInMs);
    requestFactory.setReadTimeout(readTimeoutInMs);
    requestFactory.setConnectionRequestTimeout(connectionRequestTimeoutInMs);
    return requestFactory;
  }
}
