package com.example.nvclothes.service;

import com.example.nvclothes.entity.products.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
        list.addAll(tShirtEntityService.getAllTShirtEntities());
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
                                String costFrom, String costTo, String brand, String productType){
        ListIterator<Product> iterator = searchedList.listIterator();
        String sizeE, productTypeE;
        Long costF, costT, costP;
        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!product.getProductType().getDisplayName().equals("Accessories")) {
                sizeE = product.getSize().getDisplayName();
            }
            else {
                sizeE ="";
            }
            costP = product.getCost();
            costF = Long.parseLong(costFrom);
            costT = Long.parseLong(costTo);
            productTypeE = product.getProductType().getDisplayName();
            if (!((sizeE.equals(size) || size.equals("All")) &&
                    ((costF<=costP && costP<=costT) || (costT<=costP && costP<=costF)) &&
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
        List<Product> productList = new ArrayList<>();
        productList.addAll(this.getAllProducts());
        for (Product product : productList){
            switch (product.getProductType()){
                case HOODIE:
                    HoodieEntity hoodieEntity = (HoodieEntity) product;
                    if (hoodieEntity.getHoodieId() == productId){

                        return;
                    }
                    break;
                case TSHIRT:
                    TShirtEntity tShirtEntity = (TShirtEntity) product;
                    if (tShirtEntity.getTShirtId() == productId){

                        return;
                    }
                    break;
                case TRAINERS:
                    TrainersEntity trainersEntity = (TrainersEntity) product;
                    if (trainersEntity.getTrainersId() == productId){

                        return;
                    }
                    break;
                case TROUSERS:
                    TrousersEntity trousersEntity = (TrousersEntity) product;
                    if (trousersEntity.getTrousersId() == productId){

                        return;
                    }
                    break;
                case ACCESSORY:
                    AccessoriesEntity accessoriesEntity = (AccessoriesEntity) product;
                    if (accessoriesEntity.getAccessoriesId() == productId){

                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
