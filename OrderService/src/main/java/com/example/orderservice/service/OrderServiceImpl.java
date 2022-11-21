package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
  OrderRepository orderRepository;

  @Autowired //주입하고 싶은것 : orderRepsitiory
  public OrderServiceImpl(OrderRepository orderRepository){
    this.orderRepository = orderRepository; //주입이 완료
  }

  @Override
  public OrderDto createOrder(OrderDto orderDetails) {
    orderDetails.setOrderId(UUID.randomUUID().toString());
    orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);

    orderRepository.save(orderEntity);

    OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);

    return returnValue;
  }

  @Override
  public OrderDto getOrderByOrderId(String orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

    return orderDto;
  }

  @Override
  public Iterable<OrderEntity> getOrdersByUserId(String userId) {
    return orderRepository.findByUserId(userId);
  }
}
