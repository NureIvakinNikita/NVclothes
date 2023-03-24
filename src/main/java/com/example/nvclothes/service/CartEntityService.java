package com.example.nvclothes.service;


import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.CartProductEntity;
import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.entity.products.*;
import com.example.nvclothes.exception.AccessoryNotFoundException;
import com.example.nvclothes.exception.CartNotFoundException;
import com.example.nvclothes.repository.interfaces.CartEntityRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CartEntityService {

    @Autowired
    private CartEntityRepositoryInterface cartRepository;

    @Autowired
    private CartProductEntityService cartProductEntityService;

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

    public CartEntity getCartEntityById(@Param("id") Long id) throws CartNotFoundException{
        Optional<CartEntity> cartOptional = cartRepository.getCartEntityById(id);
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException("Accessory not found for id: " + id);
        }
        return cartOptional.get();
    }

    public CartEntity getCartEntityByClientId(@Param("id") Long clientId) throws CartNotFoundException{
        Optional<CartEntity> cartOptional = cartRepository.getCartEntityByClientId(clientId);
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException("Accessory not found for id: " + clientId);
        }
        return cartOptional.get();
    }

    public void addToCart(Long productId, String productType, Long clientId) {

        switch (productType) {
            case "HOODIE":
                try {
                    HoodieEntity hoodie = hoodieEntityService.getHoodieEntityById(productId);
                    CartEntity cart = this.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice() + hoodie.getCost());
                    cart.setProductAmount(cart.getProductAmount() + 1);
                    this.save(cart);
                    cartProductEntityService.addProductToCart(hoodie.getProductId(), cart.getId(), hoodie.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "ACCESSORY":
                try {
                    AccessoriesEntity accessory = accessoriesEntityService.getAccessoryEntityById(productId);
                    CartEntity cart = this.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice() + accessory.getCost());
                    cart.setProductAmount(cart.getProductAmount() + 1);
                    this.save(cart);
                    cartProductEntityService.addProductToCart(accessory.getProductId(), cart.getId(), accessory.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TRAINERS":
                try {
                    TrainersEntity trainers = trainersEntityService.getTrainersEntityById(productId);
                    CartEntity cart = this.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice() + trainers.getCost());
                    cart.setProductAmount(cart.getProductAmount() + 1);
                    this.save(cart);
                    cartProductEntityService.addProductToCart(trainers.getProductId(), cart.getId(), trainers.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TROUSERS":
                try {
                    TrousersEntity trousers = trousersEntityService.getTrousersEntityById(productId);
                    CartEntity cart = this.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice() + trousers.getCost());
                    cart.setProductAmount(cart.getProductAmount() + 1);
                    this.save(cart);
                    cartProductEntityService.addProductToCart(trousers.getProductId(), cart.getId(), trousers.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TSHIRT":
                try {
                    TShirtEntity tShirt = tShirtEntityService.getTShirtEntityById(productId);
                    CartEntity cart = this.getCartEntityByClientId(clientId);
                    cart.setPrice(cart.getPrice() + tShirt.getCost());
                    cart.setProductAmount(cart.getProductAmount() + 1);
                    this.save(cart);
                    cartProductEntityService.addProductToCart(tShirt.getProductId(), cart.getId(), tShirt.getProductType().toString());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
        }
    }

    public List<Product> getClientProducts(Long clientId, Long cartId){
        List<Product> productList = new ArrayList<>();
        List<CartProductEntity> cartProductEntities = cartProductEntityService.getCartProductEntitiesByCartId(cartId);

        ListIterator<CartProductEntity> iterator = cartProductEntities.listIterator();
        while (iterator.hasNext()){
            CartProductEntity cartProductEntity = iterator.next();
            switch (cartProductEntity.getProductType()) {
                case "HOODIE":
                    try {
                        HoodieEntity hoodie = hoodieEntityService.getHoodieEntityById(cartProductEntity.getProductId());
                        productList.add(hoodie);
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                    break;
                case "ACCESSORY":
                    try {
                        AccessoriesEntity accessory = accessoriesEntityService.getAccessoryEntityById(cartProductEntity.getProductId());
                        productList.add(accessory);
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                    break;
                case "TRAINERS":
                    try {
                        TrainersEntity trainers = trainersEntityService.getTrainersEntityById(cartProductEntity.getProductId());
                        productList.add(trainers);
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                    break;
                case "TROUSERS":
                    try {
                        TrousersEntity trousers = trousersEntityService.getTrousersEntityById(cartProductEntity.getProductId());
                        productList.add(trousers);
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                    break;
                case "TSHIRT":
                    try {
                        TShirtEntity tShirt = tShirtEntityService.getTShirtEntityById(cartProductEntity.getProductId());
                        productList.add(tShirt);
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                    break;
                default:
                    break;
            }
        }
        return productList;
    }


    public void removeFromCart(CartEntity cart, String productType, String productId){
        switch (productType) {
            case "HOODIE":
                try {
                    HoodieEntity hoodie = hoodieEntityService.getHoodieEntityById(Long.parseLong(productId));
                    cart.setProductAmount(cart.getProductAmount()-1);
                    cart.setPrice(cart.getPrice()-hoodie.getCost());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "ACCESSORY":
                try {
                    AccessoriesEntity accessory = accessoriesEntityService.getAccessoryEntityById(Long.parseLong(productId));
                    cart.setProductAmount(cart.getProductAmount()-1);
                    cart.setPrice(cart.getPrice()-accessory.getCost());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TRAINERS":
                try {
                    TrainersEntity trainers = trainersEntityService.getTrainersEntityById(Long.parseLong(productId));
                    cart.setProductAmount(cart.getProductAmount()-1);
                    cart.setPrice(cart.getPrice()-trainers.getCost());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TROUSERS":
                try {
                    TrousersEntity trousers = trousersEntityService.getTrousersEntityById(Long.parseLong(productId));
                    cart.setProductAmount(cart.getProductAmount()-1);
                    cart.setPrice(cart.getPrice()-trousers.getCost());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            case "TSHIRT":
                try {
                    TShirtEntity tShirt = tShirtEntityService.getTShirtEntityById(Long.parseLong(productId));
                    cart.setProductAmount(cart.getProductAmount()-1);
                    cart.setPrice(cart.getPrice()-tShirt.getCost());
                } catch (Exception e) {
                    log.info(e.toString());
                }
                break;
            default:
                break;
        }
        List<CartProductEntity> cartProductEntities = cartProductEntityService.getCartProductEntitiesByCartId(cart.getId());
        ListIterator<CartProductEntity> iterator = cartProductEntities.listIterator();
        Long cartProductEntityId = null;
        while (iterator.hasNext()){
            CartProductEntity cartProductEntity = iterator.next();
            if (cartProductEntity.getProductId() == Long.parseLong(productId)
                    && cartProductEntity.getProductType().equals(productType)){
                cartProductEntityId = cartProductEntity.getCartProductId();
                break;
            }
        }

        if (cartProductEntityId != null)cartProductEntityService.deleteCartProductEntity(cartProductEntityId);//removeProduct(productType, Long.parseLong(productId));
    }

    public void deleteAllByCartId(Long cartId){
        cartProductEntityService.deleteAllByCartId(cartId);
    }

    public void deleteCartEntityById(@Param("id") Long id){
        cartRepository.deleteCartEntityById(id);
    }

    public void save(CartEntity cart){
        cartRepository.save(cart);
    }


}
