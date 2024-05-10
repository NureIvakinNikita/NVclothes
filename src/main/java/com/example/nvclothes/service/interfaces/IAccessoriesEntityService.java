package com.example.nvclothes.service.interfaces;


import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.AccessoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface IAccessoriesEntityService {

    void createAccessoryEntity(@RequestBody AccessoriesDto accessoriesDto) throws Exception;

    AccessoriesEntity getAccessoryEntityById(Long id) throws AccessoryNotFoundException;

    List<AccessoriesEntity> getAccessoriesEntitiesByBrand(String brand) throws AccessoryNotFoundException;

    List<AccessoriesEntity> getAllAccessoriesEntities();

    List<Product> searchProducts(String searchItem);

    List<Product> filter(List<Product> searchedList, FilterObject filterObject/*String size,
                                String costFrom, String costTo, String brand, String productType*/);

    List<Product> searchWithFilter(List<Product> searchedList, String searchItem);

    List<Product> sortProducts(List<Product> productList, String sortType);

    String addPhotoToAccessory(String accessoryPhoto);

    void save(AccessoriesEntity accessories);

    void addToCart(Long productId, String productType);

    void deleteAccessoriesEntitiesByProductId(Long productId);

    void deleteAccessoryEntityById(Long id);



}
