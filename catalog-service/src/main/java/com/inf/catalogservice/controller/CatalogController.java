package com.inf.catalogservice.controller;

import com.inf.catalogservice.jpa.CatalogEntity;
import com.inf.catalogservice.service.CatalogService;
import com.inf.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;

    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "Its working in catalog service : " + env.getProperty("local.server.port");
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalog> catalogs = new ArrayList<>();
        catalogList.forEach(c -> {
            catalogs.add(new ModelMapper().map(c, ResponseCatalog.class));
        });

        return ResponseEntity.ok(catalogs);
    }

}
