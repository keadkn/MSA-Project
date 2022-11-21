package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServicelmpl implements  UserService {

  UserRepository repository;
  Environment env;
  RestTemplate restTemplate;
  OrderServiceClient orderServiceClient;

  @Autowired
  public UserServicelmpl(UserRepository repository, Environment env, RestTemplate restTemplate, OrderServiceClient orderServiceClient) {
    this.repository = repository;
    this.env = env;
    this.restTemplate = restTemplate;
    this.orderServiceClient = orderServiceClient;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();
    //설정 정보가 딱 맞아야 변경 가능
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    //DB 저장을 위해서는 UserEntity 필요
    UserEntity userEntity = mapper.map(userDto, UserEntity.class);
    userEntity.setEncryptedPwd("encrypted_password");
    repository.save(userEntity);

    return null;
  }

  public UserDto getUserByUserId(String userId){
    UserEntity userEntity = repository.findByUserId(userId);
//UsernameNotFoundException ; import org.springframework.security.core.userdetails.UsernameNotFoundException;

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
    //주문서비스
    String orderUrl = String.format("http://localhost:8000/order-service/%s/orders", userId);
    ResponseEntity<List<ResponseOrder>> orderListResponse =
        restTemplate.exchange(orderUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ResponseOrder>>() {
            });
    List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);

    userDto.setOrders(orderList);
    return userDto;
  }

  @Override
  public Iterable<UserEntity> getUserByAll() {
    return repository.findAll();
  }

}
