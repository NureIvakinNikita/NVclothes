package com.example.nvclothes.service;

import com.example.nvclothes.entity.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllProductsServices {

    @Autowired
    private HoodieEntityService hoodieEntityService;

    @Autowired
    private AccessoriesEntityService accessoriesEntityService;

    @Autowired
    private TrainersEntityService trainersEntityService;

    @Autowired
    private TrousersEntityService trousersEntityService;

    @Autowired
    private TShirtEntityService tShirtEntityService;

    public List<Product> getAllProducts(){
        List<Product> list = new ArrayList<>();

        list.addAll(accessoriesEntityService.getAllAccessoriesEntities());
        list.addAll(hoodieEntityService.getAllHoodieEntities());
        list.addAll(trousersEntityService.getAllTrousersEntities());
        list.addAll(tShirtEntityService.getAllTrousersEntities());
        list.addAll(trainersEntityService.getAllTrainersEntities());

        return list;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = getAllProducts();
        List<Product> entities = new ArrayList<>();
        for (Product product : list){
            if (product.getName().equals(searchItem) || product.getName().contains(searchItem) || product.getBrand().equals(searchItem)){
                entities.add(product);
            }
        }
        return entities;
    }



}
