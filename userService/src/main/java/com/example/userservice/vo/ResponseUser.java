package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ResponseUser {
  private String email;
  private String name;
  private String userId;

  //주문데이터 값 반환
  private List<ResponseOrder> orders;
}
