package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TrainersEntity;
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

    public void createHoodieEntity(@RequestBody TrainersDto trainersDto) throws Exception{

        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TrainersEntity trainersEntity = TrainersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trainersDto.getBrand()).build();
            trainersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trainersEntity.setValue(trainersDto.getName());
            trainersEntity.setPhoto(addPhotoToTrainers(trainersDto.getPhoto()));
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.COST.getDisplayName());
            trainersEntity.setValue(trainersDto.getCost().toString());
            trainersEntity.setPhoto(addPhotoToTrainers(trainersDto.getPhoto()));
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trainersEntity.setValue(trainersDto.getSize().toString());
            trainersEntity.setPhoto(addPhotoToTrainers(trainersDto.getPhoto()));
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trainersEntity.setValue(trainersDto.getAmount().toString());
            trainersEntity.setPhoto(addPhotoToTrainers(trainersDto.getPhoto()));
            trainersRepository.save(trainersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrainersEntity getTrainersEntityById(Long id){
        return trainersRepository.getTrainersEntitiesById(id).get();
    }

    public List<TrainersEntity> getTrainersEntitiesByBrand(String brand){
        return  trainersRepository.getTrainersEntitiesByBrand(brand);
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
                    trainersEntity.setTrainersId(entity.getTrainersId());
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
        Long trainersId = list.get(list.size()).getTrainersId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "trainers_"+trainersId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String trainers_id = "trainers_"+trainersId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(trainers_id);
        return url;
    }

    public void addToCart(Long productId, String productType){

    }

    public void deleteTrainersEntityById(Long id){
        trainersRepository.deleteTrainersEntityById(id);
    }
}
