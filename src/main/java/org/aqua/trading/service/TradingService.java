package org.aqua.trading.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.aqua.trading.dto.core.TradeDto;
import org.aqua.trading.entity.*;
import org.aqua.trading.exception.BusinessException;
import org.aqua.trading.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TradingService {

  private final UserRepository userRepository;
  private final WalletRepository walletRepository;
  private final CryptoRepository cryptoRepository;
  private final OrderRepository orderRepository;
  private final WalletDetailRepository walletDetailRepository;
  private final OrderHistoryRepository orderHistoryRepository;

  @Autowired
  public TradingService(
      UserRepository userRepository,
      WalletRepository walletRepository,
      CryptoRepository cryptoRepository,
      OrderRepository orderRepository,
      WalletDetailRepository walletDetailRepository,
      OrderHistoryRepository orderHistoryRepository) {
    this.userRepository = userRepository;
    this.walletRepository = walletRepository;
    this.cryptoRepository = cryptoRepository;
    this.orderRepository = orderRepository;
    this.walletDetailRepository = walletDetailRepository;
    this.orderHistoryRepository = orderHistoryRepository;
  }

  public List<OrderHistory> retrieveOrderHistoryByUser(
      String userId, String status, LocalDateTime fromDate, LocalDateTime toDate) {
    Specification<OrderHistory> spec = (root, query, cb) -> cb.conjunction();

    if (userId != null) {
      User user =
          userRepository
              .findByIdAndStatus(UUID.fromString(userId), "N")
              .orElseThrow(
                  () ->
                      new BusinessException(
                          HttpStatus.BAD_REQUEST,
                          "User not found!",
                          MessageFormat.format("User (id: {0}) not found!", userId)));
      spec = spec.and((root, query, cb) -> cb.equal(root.get("user").get("id"), user.getId()));
    }

    if (status != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
    }

    if (fromDate != null) {
      spec =
          spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
    }

    if (toDate != null) {
      spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
    }

    List<OrderHistory> orderHistory = orderHistoryRepository.findAll(spec);

    if (orderHistory.isEmpty()) {
      throw new BusinessException(
          HttpStatus.NOT_FOUND, "No order history found!", String.valueOf(userId));
    }

    return orderHistory;
  }

  @Transactional
  public void tradeBaseOnBestAggregatedPrice(TradeDto tradeDto) {
    User user =
        userRepository
            .findByIdAndStatus(UUID.fromString(tradeDto.getUserId()), "N")
            .orElseThrow(
                () -> new BusinessException(HttpStatus.BAD_REQUEST, "User not found!", tradeDto));

    if (user.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, "User has no balance!", user.toString());
    }

    Wallet spotWallet =
        walletRepository
            .findSpotWalletByUserIdAndPriorityAndStatus(user.getId(), 1, "N")
            .orElseThrow(
                () ->
                    new BusinessException(
                        HttpStatus.BAD_REQUEST, "User has no spot wallet!", tradeDto));

    List<WalletDetail> spotWalletDetailList =
        walletDetailRepository.findAllByWalletId(spotWallet.getId());

    if (spotWallet.getCashBalance().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException(
          HttpStatus.BAD_REQUEST,
          MessageFormat.format(
              "Insufficient balance ({0}) in spot wallet!", spotWallet.getCashBalance()),
          spotWallet.toString());
    }

    Crypto crypto =
        cryptoRepository
            .findByIdAndStatus(UUID.fromString(tradeDto.getCryptoId()), "N")
            .orElseThrow(
                () ->
                    new BusinessException(
                        HttpStatus.BAD_REQUEST,
                        MessageFormat.format("Crypto (id: {0}) not found!", tradeDto.getCryptoId()),
                        tradeDto));

    WalletDetail cryptoAssetInSpotWallet =
        spotWalletDetailList.stream()
            .filter(walletDetail -> walletDetail.getCryptoId().equals(crypto.getId()))
            .findFirst()
            .orElse(null);

    Order newOrder = new Order();
    String orderStatus;

    BigDecimal remainCryptoAmount =
        cryptoAssetInSpotWallet != null ? cryptoAssetInSpotWallet.getAmount() : BigDecimal.ZERO;

    if ("B".equalsIgnoreCase(tradeDto.getBs())) {
      if (tradeDto.getAmount().compareTo(crypto.getAskQty()) > 0) {
        throw new BusinessException(
            HttpStatus.BAD_REQUEST,
            MessageFormat.format(
                "Insufficient bid quantity for crypto (id: {0} | symbol: {1})!",
                tradeDto.getCryptoId(), crypto.getSymbol()),
            crypto.toString());
      }

      if (tradeDto.getPrice().compareTo(crypto.getAsk()) > 0) {
        newOrder.setPrice(crypto.getAsk());
        orderStatus = "FLL";
      } else {
        newOrder.setPrice(tradeDto.getPrice());
        orderStatus = "P";
      }

      remainCryptoAmount = remainCryptoAmount.add(tradeDto.getAmount());
    } else {
      if (cryptoAssetInSpotWallet == null) {
        throw new BusinessException(
            HttpStatus.BAD_REQUEST,
            MessageFormat.format(
                "Crypto (id: {0} | symbol: {1}) not found in spot wallet!",
                tradeDto.getCryptoId(), crypto.getSymbol()));
      }

      if (tradeDto.getAmount().compareTo(cryptoAssetInSpotWallet.getAmount()) > 0) {
        throw new BusinessException(
            HttpStatus.BAD_REQUEST,
            MessageFormat.format(
                "Insufficient crypto amount for sell symbol: {0}) | amount: {1}!",
                crypto.getSymbol(), cryptoAssetInSpotWallet.getAmount()),
            crypto.toString());
      }

      if (tradeDto.getPrice().compareTo(crypto.getBid()) < 0) {
        newOrder.setPrice(crypto.getBid());
        orderStatus = "FLL";
      } else {
        newOrder.setPrice(tradeDto.getPrice());
        orderStatus = "P";
      }

      remainCryptoAmount = remainCryptoAmount.subtract(tradeDto.getAmount());
    }

    newOrder.setId(UUID.randomUUID());
    newOrder.setAmount(tradeDto.getAmount());
    newOrder.setBs(tradeDto.getBs());
    newOrder.setStatus(orderStatus);
    newOrder.setUser(user);
    newOrder.setCrypto(crypto);
    newOrder.setCreatedAt(LocalDateTime.now());
    newOrder.setUpdatedAt(LocalDateTime.now());

    if ("FLL".equals(orderStatus)) {
      WalletDetail walletDetail = new WalletDetail();
      walletDetail.setWalletId(spotWallet.getId());
      walletDetail.setCryptoId(crypto.getId());
      walletDetail.setAmount(remainCryptoAmount);
      walletDetail.setStatus(remainCryptoAmount.compareTo(BigDecimal.ZERO) <= 0 ? "D" : "N");

      if (cryptoAssetInSpotWallet == null) {
        walletDetail.setCreatedAt(LocalDateTime.now());
      }

      walletDetail.setUpdatedAt(LocalDateTime.now());
      walletDetailRepository.save(walletDetail);

      OrderHistory orderHistory = new OrderHistory();
      orderHistory.setOrderId(newOrder.getId());
      orderHistory.setUser(user);
      orderHistory.setCrypto(crypto);
      orderHistory.setPrice(newOrder.getPrice());
      orderHistory.setAmount(newOrder.getAmount());
      orderHistory.setBs(newOrder.getBs());
      orderHistory.setStatus(orderStatus);
      orderHistory.setCreatedAt(newOrder.getCreatedAt());
      orderHistory.setUpdatedAt(newOrder.getUpdatedAt());

      orderHistoryRepository.save(orderHistory);

      BigDecimal remainUserBalance = user.getBalance();
      BigDecimal remainSpotWalletBalance = spotWallet.getCashBalance();
      if ("B".equalsIgnoreCase(newOrder.getBs())) {
        remainUserBalance = remainUserBalance.subtract(newOrder.getPrice());
        remainSpotWalletBalance = remainSpotWalletBalance.subtract(newOrder.getPrice());
      } else {
        remainUserBalance = remainUserBalance.add(newOrder.getPrice());
        remainSpotWalletBalance = remainSpotWalletBalance.add(newOrder.getPrice());
      }

      user.setBalance(remainUserBalance);
      spotWallet.setCashBalance(remainSpotWalletBalance);
    }

    orderRepository.save(newOrder);
    userRepository.save(user);
    walletRepository.save(spotWallet);
  }
}
