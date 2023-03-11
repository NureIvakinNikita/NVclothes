package com.example.nvclothes.service;

import com.example.nvclothes.entity.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

import java.util.*;

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
            if (product.getName().equals(searchItem) || product.getName().contains(searchItem)
                    || product.getBrand().getDisplayName().equals(searchItem)){
                entities.add(product);
            }
        }
        return entities;
    }

    public List<Product> filter(List<Product> searchedList, String size,
                                String cost, String brand, String productType){
        ListIterator<Product> iterator = searchedList.listIterator();
        String sizeE, costE, productTypeE;
        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!product.getProductType().getDisplayName().equals("Accessories")) {
                sizeE = product.getSize().getDisplayName();
            }
            else {
                sizeE ="";
            }
            costE = product.getCost().toString();
            productTypeE = product.getProductType().getDisplayName();
            if (!((sizeE.equals(size) || size.equals("All")) &&
                    (costE.equals(cost) || cost.equals("All")) &&
                    (product.getBrand().getDisplayName().equals(brand) || brand.equals("All")) &&
                    (productTypeE.equals(productType) || productType.equals("All")))){
                iterator.remove();
            }
        }
        return searchedList;
    }

    public List<Product> searchWithFilter(List<Product> searchedList, String searchItem){
        ListIterator<Product> iterator = searchedList.listIterator();

        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!(product.getName().equals(searchItem) || product.getName().contains(searchItem)
                    || product.getBrand().getDisplayName().equals(searchItem))){
                iterator.remove();
            }
        }

        return searchedList;
    }

    public List<Product> sortProducts(List<Product> productList, String sortType){
        switch (sortType){
            case "By brand name from a to z":
                Comparator<Product> byBrandAToZ = Comparator.comparing(Product::getBrand);
                Collections.sort(productList, byBrandAToZ);
                break;
            case "By brand name from z to a":
                Comparator<Product> byBrandZToA = Comparator.comparing(Product::getBrand).reversed();
                Collections.sort(productList, byBrandZToA);
                break;
            case "By price from low to high":
                Comparator<Product> priceLowToHigh = Comparator.comparing(Product::getCost);
                Collections.sort(productList, priceLowToHigh);
                break;
            case "By price from high to low":
                Comparator<Product> priceHighToLow = Comparator.comparing(Product::getCost).reversed();
                Collections.sort(productList, priceHighToLow);
                break;
            default:
                productList = this.getAllProducts();
                break;
        }
        return productList;
    }

    public void addToCart(Long productId, String productType){

    }


}
