package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.HoodieEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.exception.TrainersNotFoundException;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.TrainersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainersEntityService {

    private final TrainersEntityRepositoryInterface trainersRepository;

    public void createTrainersEntity(@RequestBody TrainersDto trainersDto) throws Exception{


        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TrainersEntity trainersEntity = TrainersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trainersDto.getBrand())
                    .photo(trainersDto.getPhoto())
                    .productId(trainersDto.getTrainersId())
                    .id(trainersDto.getId()).build();

            trainersRepository.save(trainersEntity);

            trainersEntity.setId(trainersEntity.getId()+1L);
            trainersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trainersEntity.setValue(trainersDto.getName());
            trainersEntity.setPhoto(trainersDto.getPhoto());
            trainersRepository.save(trainersEntity);


            trainersEntity.setId(trainersEntity.getId()+1L);
            trainersEntity.setAttribute(Attribute.COST.getDisplayName());
            trainersEntity.setValue(trainersDto.getCost().toString());
            trainersEntity.setPhoto(trainersDto.getPhoto());
            trainersRepository.save(trainersEntity);

            trainersEntity.setId(trainersEntity.getId()+1L);

            trainersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trainersEntity.setValue(trainersDto.getSize().getDisplayName());
            trainersEntity.setPhoto(trainersDto.getPhoto());
            trainersRepository.save(trainersEntity);

            trainersEntity.setId(trainersEntity.getId()+1L);
            trainersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trainersEntity.setValue(trainersDto.getAmount().toString());
            trainersEntity.setPhoto(trainersDto.getPhoto());
            trainersRepository.save(trainersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrainersEntity getTrainersEntityById(Long id) throws TrainersNotFoundException{
        List<TrainersEntity> trainers = trainersRepository.getTrainersEntitiesByProductId(id);
        TrainersEntity trainersEntity = TrainersEntity.builder().build();
        Comparator<TrainersEntity> byId = Comparator.comparing(TrainersEntity::getId);
        Collections.sort(trainers, byId);
        Long numericValues;
        if (trainers.size() == 0) {
            throw new TrainersNotFoundException("Trainers not found for id: " + id);
        } else {
            for (TrainersEntity entity : trainers) {
                switch (entity.getAttribute()) {
                    case "BRAND":
                        trainersEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                        break;
                    case "NAME":
                        trainersEntity.setName(entity.getValue());
                        break;
                    case "COST":
                        numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                        trainersEntity.setCost(numericValues);
                        break;
                    case "SIZE":
                        trainersEntity.setSize(Size.fromDisplayName(entity.getValue()));
                        break;
                    case "AMOUNT":
                        numericValues = Long.parseLong(entity.getValue());
                        trainersEntity.setAmount(numericValues);
                        trainersEntity.setId(entity.getId());
                        trainersEntity.setProductId(entity.getProductId());
                        trainersEntity.setProductType(ProductType.TRAINERS);
                        trainersEntity.setPhoto(entity.getPhoto());
                        return trainersEntity;
                    default:
                        break;
                }

            }
        }
        throw new TrainersNotFoundException("Trainers not found for id: " + id);
    }

    public List<TrainersEntity> getTrainersEntitiesByBrand(String brand) throws TrainersNotFoundException{
        List<TrainersEntity> trainers = new ArrayList<>();
        trainers.addAll(trainersRepository.getTrainersEntitiesByBrand(brand));
        if (trainers.size() == 0) {
            throw new TrainersNotFoundException("Trainers not found for brand: " + brand);
        }

        return trainers;
    }

    public List<TrainersEntity> getAllTrainersEntities(){
        List<TrainersEntity> list = trainersRepository.findAll();
        Comparator<TrainersEntity> byId = Comparator.comparing(TrainersEntity::getId);
        Collections.sort(list, byId);
        List<TrainersEntity> entities = new ArrayList<>();
        TrainersEntity trainersEntity = TrainersEntity.builder().build();
        Long numericValues;
        for (TrainersEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    trainersEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    trainersEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    trainersEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    trainersEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    trainersEntity.setAmount(numericValues);
                    trainersEntity.setId(entity.getId());
                    trainersEntity.setProductId(entity.getProductId());
                    trainersEntity.setProductType(ProductType.TRAINERS);
                    trainersEntity.setPhoto(entity.getPhoto());
                    entities.add(trainersEntity);
                    trainersEntity = trainersEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = new ArrayList<>();
        list.addAll(getAllTrainersEntities());
        List<Product> entities = new ArrayList<>();
        for (Product product : list){
            if (product.getName().equals(searchItem) || product.getName().contains(searchItem)
                    || product.getBrand().getDisplayName().equals(searchItem)){
                entities.add(product);
            }
        }
        return entities;
    }

    public List<Product> filter(List<Product> searchedList, FilterObject filterObject/*String size,
                                String costFrom, String costTo, String brand, String productType*/){
        /*= searchedList.listIterator();*/
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(this.getAllTrainersEntities());
        searchedList =  new ArrayList<>();
        ListIterator<Product> iterator = allProducts.listIterator();
        String sizeE, productTypeE;
        Long costF, costT, costP;
        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!product.getProductType().getDisplayName().equals("Accessories")) {
                sizeE = product.getSize().getDisplayName();
            }
            else {
                sizeE ="";
                filterObject.setSize("All");
            }
            costP = product.getCost();

            costF = filterObject.getCostFrom();
            costT = filterObject.getCostTo();
            if (costF < 0) costF = 0L;
            if (costT < 0) costT = 0L;
            productTypeE = product.getProductType().getDisplayName();
            if (((sizeE.equals(filterObject.getSize()) || filterObject.getSize().equals("All") || sizeE.equals("")) &&
                    ((costF<=costP && costP<=costT) || (costT<=costP && costP<=costF) || (costT == 0 && costF == 0)) &&
                    (product.getBrand().getDisplayName().equals(filterObject.getBrand()) || filterObject.getBrand().equals("All")) &&
                    (productTypeE.equals(filterObject.getProductType()) || filterObject.getProductType().equals("All")))){
                searchedList.add(product);
            } else if (searchedList.contains(product)) {
                searchedList.remove(product);
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
                productList.addAll(getAllTrainersEntities());
                break;
        }
        return productList;
    }

    public String addPhotoToTrainers(String hoodiePhoto){
        Map config = new HashMap();
        String cloudName, api_key, api_secret;
        cloudName = System.getenv("CLOUD_NAME");
        api_key = System.getenv("API_KEY");
        api_secret = System.getenv("API_SECRET");
        config.put("cloud_name", cloudName);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);
        List<TrainersEntity> list = new ArrayList<>();
        list.addAll(getAllTrainersEntities());
        Long trainersId = list.get(list.size()).getProductId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "trainers_"+trainersId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String trainers_id = "trainers_"+trainersId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(trainers_id);
        return url;
    }

    public void save(TrainersEntity trainers){

        Optional<TrainersEntity> optionalEntity = trainersRepository.getTrainersEntityByProductIdAndAttribute(trainers.getProductId(), "BRAND");
        if (optionalEntity.isPresent()) {
            TrainersEntity entity = optionalEntity.get();
            entity.setAttribute("BRAND");
            entity.setValue(trainers.getBrand().getDisplayName());
            entity.setProductId(trainers.getProductId());
            entity.setPhoto(trainers.getPhoto());
            trainersRepository.save(entity);
        }


        optionalEntity = trainersRepository.getTrainersEntityByProductIdAndAttribute(trainers.getProductId(), "NAME");
        if (optionalEntity.isPresent()) {
            TrainersEntity entity = optionalEntity.get();
            entity.setAttribute("NAME");
            entity.setValue(trainers.getName());
            entity.setProductId(trainers.getProductId());
            trainersRepository.save(entity);
        }


        optionalEntity = trainersRepository.getTrainersEntityByProductIdAndAttribute(trainers.getProductId(), "COST");
        if (optionalEntity.isPresent()) {
            TrainersEntity entity = optionalEntity.get();
            entity.setAttribute("COST");
            entity.setValue(trainers.getCost().toString());
            entity.setProductId(trainers.getProductId());
            trainersRepository.save(entity);
        }

        optionalEntity = trainersRepository.getTrainersEntityByProductIdAndAttribute(trainers.getProductId(), "SIZE");
        if (optionalEntity.isPresent()) {
            TrainersEntity entity = optionalEntity.get();
            entity.setAttribute("SIZE");
            entity.setValue(trainers.getSize().getDisplayName());
            entity.setProductId(trainers.getProductId());
            trainersRepository.save(entity);
        }

        optionalEntity = trainersRepository.getTrainersEntityByProductIdAndAttribute(trainers.getProductId(), "AMOUNT");
        if (optionalEntity.isPresent()) {
            TrainersEntity entity = optionalEntity.get();
            entity.setAttribute("AMOUNT");
            entity.setValue(trainers.getAmount().toString());
            entity.setProductId(trainers.getProductId());
            trainersRepository.save(entity);
        }

    }
    public void addToCart(Long productId, String productType){

    }

    public void deleteTrainersEntitiesByProductId(Long productId){
        trainersRepository.deleteTrainersEntitiesByProductId(productId);
    }

    public void deleteTrainersEntityById(Long id){
        trainersRepository.deleteTrainersEntityById(id);
    }
}
