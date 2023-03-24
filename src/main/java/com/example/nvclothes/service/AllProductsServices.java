package com.example.nvclothes.service;

import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.products.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
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

    @Autowired
    private CartEntityService cartEntityService;

    @Autowired
    private CartProductEntityService cartProductEntityService;

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
            if (costFrom.equals("")) costFrom="0";
            if (costTo.equals("")) costTo="0";
            try {
                costF = Long.parseLong(costFrom);
                costT = Long.parseLong(costTo);
            } catch (Exception e){
                costFrom="0";
                costTo="0";
                costF = 0L;
                costT = 0L;
            }
            costF = Long.parseLong(costFrom);
            costT = Long.parseLong(costTo);
            if (costF < 0) costF = 0L;
            if (costT < 0) costT = 0L;
            productTypeE = product.getProductType().getDisplayName();
            if (!((sizeE.equals(size) || size.equals("All")) &&
                    ((costF<=costP && costP<=costT) || (costT<=costP && costP<=costF) || (costT == 0 && costF == 0)) &&
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

    public void addToCart(Long productId, String productType, Long clientId){

        switch (productType) {
            case "HOODIE":
                try {
                    HoodieEntity hoodie = hoodieEntityService.getHoodieEntityById(productId);
                    CartEntity cart = cartEntityService.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice()+hoodie.getCost());
                    cart.setProductAmount(cart.getProductAmount()+1);
                    cartEntityService.save(cart);
                    cartProductEntityService.addProductToCart(hoodie.getProductId(), cart.getId(), hoodie.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "ACCESSORY":
                try {
                    AccessoriesEntity accessory = accessoriesEntityService.getAccessoryEntityById(productId);
                    CartEntity cart = cartEntityService.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice()+accessory.getCost());
                    cart.setProductAmount(cart.getProductAmount()+1);
                    cartEntityService.save(cart);
                    cartProductEntityService.addProductToCart(accessory.getProductId(), cart.getId(), accessory.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TRAINERS":
                try {
                    TrainersEntity trainers = trainersEntityService.getTrainersEntityById(productId);
                    CartEntity cart = cartEntityService.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice()+trainers.getCost());
                    cart.setProductAmount(cart.getProductAmount()+1);
                    cartEntityService.save(cart);
                    cartProductEntityService.addProductToCart(trainers.getProductId(), cart.getId(), trainers.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TROUSERS":
                try {
                    TrousersEntity trousers = trousersEntityService.getTrousersEntityById(productId);
                    CartEntity cart = cartEntityService.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice()+trousers.getCost());
                    cart.setProductAmount(cart.getProductAmount()+1);
                    cartEntityService.save(cart);
                    cartProductEntityService.addProductToCart(trousers.getProductId(), cart.getId(), trousers.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TSHIRT":
                try {
                    TShirtEntity tShirt = tShirtEntityService.getTShirtEntityById(productId);
                    CartEntity cart = cartEntityService.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice()+tShirt.getCost());
                    cart.setProductAmount(cart.getProductAmount()+1);
                    cartEntityService.save(cart);
                    cartProductEntityService.addProductToCart(tShirt.getProductId(), cart.getId(), tShirt.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            default:
                break;
        }
        /*List<Product> productList = new ArrayList<>();
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
                    if (accessoriesEntity.getProductId() == productId){

                        return;
                    }
                    break;
                default:
                    break;
            }
        }*/
    }
}
