package org.aqua.trading.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.aqua.trading.dto.core.TradeDto;
import org.aqua.trading.entity.*;
import org.aqua.trading.exception.BusinessException;
import org.aqua.trading.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    if (spotWallet.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException(
          HttpStatus.BAD_REQUEST,
          MessageFormat.format(
              "Insufficient balance ({0}) in spot wallet!", spotWallet.getBalance()),
          spotWallet.toString());
    }

    Crypto crypto =
        Optional.of(cryptoRepository.findBySymbolAndStatus(tradeDto.getCryptoId(), "N"))
            .orElseThrow(
                () ->
                    new BusinessException(
                        HttpStatus.BAD_REQUEST,
                        MessageFormat.format("Crypto (id: {0}) not found!", tradeDto.getCryptoId()),
                        tradeDto));

    Order newOrder = new Order();
    String orderStatus;

    if ("B".equalsIgnoreCase(tradeDto.getBs())) {
      if (tradeDto.getAmount().compareTo(crypto.getBidQty()) > 0) {
        throw new BusinessException(
            HttpStatus.BAD_REQUEST,
            MessageFormat.format(
                "Insufficient bid quantity for crypto (id: {0} | symbol: {1})!",
                tradeDto.getCryptoId(), crypto.getSymbol()),
            crypto.toString());
      }

      if (tradeDto.getPrice().compareTo(crypto.getBid()) > 0) {
        newOrder.setPrice(crypto.getBid());
        orderStatus = "FLL";
      } else {
        newOrder.setPrice(tradeDto.getPrice());
        orderStatus = "P";
      }
    } else {
      if (tradeDto.getAmount().compareTo(crypto.getAskQty()) > 0) {
        throw new BusinessException(
            HttpStatus.BAD_REQUEST,
            MessageFormat.format(
                "Insufficient ask quantity for crypto (id: {0} | symbol: {1})!",
                tradeDto.getCryptoId(), crypto.getSymbol()),
            crypto.toString());
      }

      if (tradeDto.getPrice().compareTo(crypto.getAsk()) < 0) {
        newOrder.setPrice(crypto.getAsk());
        orderStatus = "FLL";
      } else {
        newOrder.setPrice(tradeDto.getPrice());
        orderStatus = "P";
      }
    }

    newOrder.setId(UUID.randomUUID());
    newOrder.setAmount(tradeDto.getAmount());
    newOrder.setBs(tradeDto.getBs());
    newOrder.setStatus(orderStatus);
    newOrder.setUser(user);
    newOrder.setCrypto(crypto);
    newOrder.setCreatedAt(LocalDateTime.now());
    newOrder.setUpdatedAt(LocalDateTime.now());

    user.setBalance(user.getBalance().subtract(newOrder.getPrice()));
    spotWallet.setBalance(spotWallet.getBalance().subtract(newOrder.getPrice()));

    if ("FLL".equals(orderStatus)) {
      WalletDetail walletDetail = new WalletDetail();
      walletDetail.setId(UUID.randomUUID());
      walletDetail.setWallet(spotWallet);
      walletDetail.setCrypto(crypto);
      walletDetail.setAmount(newOrder.getPrice());
      walletDetail.setStatus("N");
      walletDetail.setCreatedAt(LocalDateTime.now());
      walletDetail.setUpdatedAt(LocalDateTime.now());
      walletDetailRepository.save(walletDetail);
    }

    orderRepository.save(newOrder);
    orderHistoryRepository.save(newOrder);
    userRepository.save(user);
    walletRepository.save(spotWallet);
  }
}
