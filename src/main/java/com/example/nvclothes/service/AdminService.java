package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.dto.*;
import com.example.nvclothes.entity.products.*;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import com.example.nvclothes.service.interfaces.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AdminService implements IAdminService {

    @Autowired
    private HoodieEntityService hoodieEntityService;
    @Autowired
    private IAccessoriesEntityService accessoriesEntityService;
    @Autowired
    private TrousersEntityService trousersEntityService;
    @Autowired
    private TrainersEntityService trainersEntityService;
    @Autowired
    private TShirtEntityService tShirtEntityService;

    public boolean validateData(String name, Long cost, Long amount, String size){
        String regex = "^[a-zA-Z0-9]+$";
        boolean flag = name.matches(regex);
        flag = cost > 0;
        flag = cost < 100000;
        flag = amount > 0;
        flag = amount < 1000;
        if (name.matches(regex) && (cost>0 && cost<100000) && (amount >0 && amount < 1000)){
            return true;
        }
        return false;
    }
    public boolean edit(Long productId, String productType, String brand, String name, Long cost, Long amount, String size){
        if (validateData(name, cost, amount, size)){
            log.info("Not valid data");
            return false;
        }
        switch (productType){
            case "HOODIE":
                try {
                    HoodieEntity hoodie = hoodieEntityService.getHoodieEntityById(productId);
                    hoodie.setName(name);
                    hoodie.setSize(Size.fromDisplayName(size));
                    hoodie.setAmount(amount);
                    hoodie.setBrand(Brand.fromDisplayName(brand));
                    hoodie.setCost(cost);
                    hoodieEntityService.save(hoodie);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "ACCESSORY":
                try {
                    AccessoriesEntity accessory = accessoriesEntityService.getAccessoryEntityById(productId);
                    accessory.setName(name);
                    accessory.setAmount(amount);
                    accessory.setBrand(Brand.fromDisplayName(brand));
                    accessory.setCost(cost);
                    accessoriesEntityService.save(accessory);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TRAINERS":
                try {
                    TrainersEntity trainers = trainersEntityService.getTrainersEntityById(productId);
                    trainers.setName(name);
                    trainers.setSize(Size.fromDisplayName(size));
                    trainers.setAmount(amount);
                    trainers.setBrand(Brand.fromDisplayName(brand));
                    trainers.setCost(cost);
                    trainersEntityService.save(trainers);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TROUSERS":
                try {
                    TrousersEntity trousers = trousersEntityService.getTrousersEntityById(productId);
                    trousers.setName(name);
                    trousers.setSize(Size.fromDisplayName(size));
                    trousers.setAmount(amount);
                    trousers.setBrand(Brand.fromDisplayName(brand));
                    trousers.setCost(cost);
                    trousersEntityService.save(trousers);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TSHIRT":
                try {
                    TShirtEntity tShirt = tShirtEntityService.getTShirtEntityById(productId);
                    tShirt.setName(name);
                    tShirt.setSize(Size.fromDisplayName(size));
                    tShirt.setAmount(amount);
                    tShirt.setBrand(Brand.fromDisplayName(brand));
                    tShirt.setCost(cost);
                    tShirtEntityService.save(tShirt);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    public boolean delete(Long productId, String productType){
        switch (productType){
            case "HOODIE":
                try {

                    hoodieEntityService.deleteHoodieEntitiesByProductId(productId);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "ACCESSORY":
                try {

                    accessoriesEntityService.deleteAccessoriesEntitiesByProductId(productId);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TRAINERS":
                try {

                    trainersEntityService.deleteTrainersEntitiesByProductId(productId);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TROUSERS":
                try {

                    trousersEntityService.deleteTrousersEntitiesByProductId(productId);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TSHIRT":
                try {

                    tShirtEntityService.deleteTShirtEntitiesByProductId(productId);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            default:
                return false;
        }

    }

    public boolean addProduct(String photo, String brand, String name, Long cost, String size, Long amount, String type){

        Map config = new HashMap();
        String cloudName, api_key, api_secret;
        cloudName = System.getenv("CLOUD_NAME");
        api_key = System.getenv("API_KEY");
        api_secret = System.getenv("API_SECRET");
        config.put("cloud_name", cloudName);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);

        switch (type.toUpperCase()){
            case "HOODIES":
                try {
                    List<HoodieEntity> list = hoodieEntityService.getAllHoodieEntities();
                    HoodieDto hoodieDto = HoodieDto.builder()
                            .amount(amount)
                            .cost(cost)
                            .name(name)
                            .brand(brand)
                            .hoodieId(list.size()+1L)
                            .size(Size.fromDisplayName(size)).build();
                    cloudinary.uploader().upload(photo, ObjectUtils.asMap("public_id", "hoodie_"+(list.size()+1)));
                    String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("hoodie_"+(list.size()+1));
                    hoodieDto.setPhoto(url);
                    hoodieEntityService.createHoodieEntity(hoodieDto);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "ACCESSORIES":
                try {
                    List<AccessoriesEntity> list = accessoriesEntityService.getAllAccessoriesEntities();
                    AccessoriesDto accessoriesDto = AccessoriesDto.builder()
                            .amount(amount)
                            .cost(cost)
                            .name(name)
                            .brand(brand)
                            .accessoriesId(list.size()+1L)
                            .build();
                    cloudinary.uploader().upload(photo, ObjectUtils.asMap("public_id", "accessory_"+(list.size()+1)));
                    String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("accessory_"+(list.size()+1));
                    accessoriesDto.setPhoto(url);
                    accessoriesEntityService.createAccessoryEntity(accessoriesDto);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TRAINERS":
                try {
                    List<TrainersEntity> list = trainersEntityService.getAllTrainersEntities();
                    TrainersDto trainersDto = TrainersDto.builder()
                            .amount(amount)
                            .cost(cost)
                            .name(name)
                            .brand(brand)
                            .size(Size.fromDisplayName(size))
                            .trainersId(list.size()+1L)
                            .id(list.get(list.size()-1).getId()+1L).build();
                    cloudinary.uploader().upload(photo, ObjectUtils.asMap("public_id", "trainers_"+(list.size()+1)));
                    String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trainers_"+(list.size()+1));
                    trainersDto.setPhoto(url);
                    trainersEntityService.createTrainersEntity(trainersDto);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "TROUSERS":
                try {
                    List<TrousersEntity> list = trousersEntityService.getAllTrousersEntities();
                    TrousersDto trousersDto = TrousersDto.builder()
                            .amount(amount)
                            .cost(cost)
                            .name(name)
                            .brand(brand)
                            .trousersId(list.size()+1L)
                            .size(Size.fromDisplayName(size)).build();
                    cloudinary.uploader().upload(photo, ObjectUtils.asMap("public_id", "trousers_"+(list.size()+1)));
                    String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("trousers_"+(list.size()+1));
                    trousersDto.setPhoto(url);
                    trousersEntityService.createTrousersEntity(trousersDto);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            case "T-SHIRTS":
                try {
                    List<TShirtEntity> list = tShirtEntityService.getAllTShirtEntities();
                    TShirtDto tShirtDto = TShirtDto.builder()
                            .amount(amount)
                            .cost(cost)
                            .name(name)
                            .brand(brand)
                            .tShirtId(list.size()+1L)
                            .size(Size.fromDisplayName(size)).build();
                    cloudinary.uploader().upload(photo, ObjectUtils.asMap("public_id", "t_shirt_"+(list.size()+1)));
                    String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate("t_shirt_"+(list.size()+1));
                    tShirtDto.setPhoto(url);
                    tShirtEntityService.createTShirtEntity(tShirtDto);
                } catch (Exception e) {
                    log.info(e.toString());
                    return false;
                }
                return true;
            default:
                return false;
        }

    }
}
