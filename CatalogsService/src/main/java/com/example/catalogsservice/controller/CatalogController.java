package com.example.catalogsservice.controller;

import com.example.catalogsservice.jpa.CatalogEntity;
import com.example.catalogsservice.service.CatalogService;
import com.example.catalogsservice.vo.ResponseCatalog;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
  private Environment env;
  CatalogService catalogService;

  @Autowired
  public CatalogController(Environment env, CatalogService catalogService) {
    this.env = env;
    this.catalogService = catalogService;
  }

  @GetMapping("/health_check")
  public String status(HttpServletRequest request) {
    return String.format("It's Working in User Service on Port %s", request.getServerPort());

  }

  @GetMapping(value="catalogs")
  public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
    Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

    List<ResponseCatalog> result = new ArrayList<>();
    catalogList.forEach(v -> {
      result.add(new ModelMapper().map(v, ResponseCatalog.class));
    });
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
