package br.com.product.api.productapi.modules.supplier.controller;

import br.com.product.api.productapi.modules.supplier.dto.SupplierRequest;
import br.com.product.api.productapi.modules.supplier.dto.SupplierResponse;
import br.com.product.api.productapi.modules.supplier.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
public class SupplierController {

    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse create(@RequestBody SupplierRequest request) {
        return supplierService.create(request);
    }
}