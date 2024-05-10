package com.example.nvclothes.controller.rest;

import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.service.AccessoriesEntityService;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccessoriesRestControllerTest {

    @Mock
    private IAccessoriesEntityService accessoriesService;

    @Mock
    private FilterObject filterObject;

    @InjectMocks
    private AccessoriesRestController accessoriesRestController;

    private List<AccessoriesEntity> accessoriesList;

    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accessoriesList = new ArrayList<>();
        AccessoriesEntity accessory = AccessoriesEntity.builder()
                .amount(3L)
                .brand(Brand.NEW_BALANCE)
                .cost(30L)
                .name("NY cap")
                .productType(ProductType.ACCESSORY).build();
        accessoriesList.add(accessory);
        accessory = AccessoriesEntity.builder()
                .amount(5L)
                .brand(Brand.ADIDAS)
                .cost(20L)
                .name("Sport bag")
                .productType(ProductType.ACCESSORY).build();
        accessoriesList.add(accessory);

        productList = new ArrayList<>();

        productList.addAll(accessoriesList);
    }

    @Test
    void testGetAllProducts() {
        when(accessoriesService.getAllAccessoriesEntities()).thenReturn(accessoriesList);

        ResponseEntity<List<Product>> response = accessoriesRestController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessoriesList, response.getBody());
    }

    @Test
    void testSearchProducts() {
        when(accessoriesService.searchProducts(anyString())).thenReturn(productList);

        ResponseEntity<List<Product>> response = accessoriesRestController.searchProducts("search");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessoriesList, response.getBody());
    }

    @Test
    void testSearchProducts_filteredList() {
        List<Product> filteredList = new ArrayList<>();
        filteredList.add(AccessoriesEntity.builder()
                .amount(7L)
                .brand(Brand.NIKE)
                .cost(20L)
                .name("Gloves")
                .productType(ProductType.ACCESSORY).build());
        when(accessoriesService.searchWithFilter(anyList(), anyString())).thenReturn(filteredList);
        ResponseEntity<List<Product>> response = accessoriesRestController.searchProducts("search");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredList, response.getBody());
    }

    @Test
    void testFilterProducts() {
        when(filterObject.getCostFrom()).thenReturn(0L);
        when(filterObject.getCostTo()).thenReturn(10L);
        when(accessoriesService.getAllAccessoriesEntities()).thenReturn(accessoriesList);
        when(accessoriesService.filter(anyList(), any(FilterObject.class))).thenReturn(productList);

        ResponseEntity<?> response = accessoriesRestController.filterProducts(filterObject, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessoriesList, response.getBody());
    }

    @Test
    void testFilterProducts_costFromBigger() {
        when(filterObject.getCostFrom()).thenReturn(10L);
        when(filterObject.getCostTo()).thenReturn(0L);

        ResponseEntity<?> response = accessoriesRestController.filterProducts(filterObject, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals(1, errors.size());
        assertEquals("CostFrom can not be bigger than CostTo.", errors.get("costFromBiggerError"));
    }

    @Test
    void testFilterProducts_validationErrors() {
        when(filterObject.getCostFrom()).thenReturn(0L);
        when(filterObject.getCostTo()).thenReturn(10L);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(ControllerUitls.getErrors(any())).thenReturn(Map.of("error", "error message"));

        ResponseEntity<?> response = accessoriesRestController.filterProducts(filterObject, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals(1, errors.size());
        assertEquals("error message", errors.get("error"));
    }


    @Test
    void testClearFilters() {
        when(accessoriesService.getAllAccessoriesEntities()).thenReturn(accessoriesList);

        ResponseEntity<List<Product>> response = accessoriesRestController.clearFilters();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessoriesList, response.getBody());
    }
}