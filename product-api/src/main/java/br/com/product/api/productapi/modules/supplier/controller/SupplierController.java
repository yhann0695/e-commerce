package br.com.product.api.productapi.modules.supplier.controller;

import br.com.product.api.productapi.configuration.exception.SuccessResponse;
import br.com.product.api.productapi.modules.supplier.dto.SupplierRequest;
import br.com.product.api.productapi.modules.supplier.dto.SupplierResponse;
import br.com.product.api.productapi.modules.supplier.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
public class SupplierController {

    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse create(@RequestBody SupplierRequest request) {
        return supplierService.create(request);
    }

    @GetMapping
    public List<SupplierResponse> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

    @GetMapping("/{id}")
    public SupplierResponse findById(@PathVariable Integer id) {
        return supplierService.findByIdResponse(id);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return supplierService.delete(id);
    }
}
