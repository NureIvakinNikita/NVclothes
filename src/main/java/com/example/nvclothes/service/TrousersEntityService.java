package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.dto.TrousersDto;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.TrousersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrousersEntityService {

    private final TrousersEntityRepositoryInterface trousersRepository;

    public void createHoodieEntity(@RequestBody TrousersDto trousersDto) throws Exception{

        if (trousersDto.getCost() != null && trousersDto.getBrand()!=null
                && trousersDto.getName()!=null && trousersDto.getAmount()!=null){
            TrousersEntity trousersEntity = TrousersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trousersDto.getBrand()).build();
            trousersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trousersEntity.setValue(trousersDto.getName());
            trousersEntity.setPhoto(addPhotoToTrousers(trousersDto.getPhoto()));
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.COST.getDisplayName());
            trousersEntity.setValue(trousersDto.getCost().toString());
            trousersEntity.setPhoto(addPhotoToTrousers(trousersDto.getPhoto()));
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trousersEntity.setValue(trousersDto.getSize().toString());
            trousersEntity.setPhoto(addPhotoToTrousers(trousersDto.getPhoto()));
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trousersEntity.setValue(trousersDto.getAmount().toString());
            trousersEntity.setPhoto(addPhotoToTrousers(trousersDto.getPhoto()));
            trousersRepository.save(trousersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrousersEntity getTrousersEntityById(Long id){
        return trousersRepository.getTrousersEntitiesById(id).get();
    }

    public List<TrousersEntity> getTrousersEntitiesByBrand(String brand){
        return  trousersRepository.getTrousersEntitiesByBrand(brand);
    }

    public List<TrousersEntity> getAllTrousersEntities(){
        List<TrousersEntity> list = trousersRepository.findAll();
        Comparator<TrousersEntity> byId = Comparator.comparing(TrousersEntity::getId);
        Collections.sort(list, byId);
        List<TrousersEntity> entities = new ArrayList<>();
        TrousersEntity trousersEntity = TrousersEntity.builder().build();
        Long numericValues;
        for (TrousersEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    trousersEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    trousersEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    trousersEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    trousersEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    trousersEntity.setAmount(numericValues);
                    trousersEntity.setId(entity.getId());
                    trousersEntity.setTrousersId(entity.getTrousersId());
                    trousersEntity.setProductType(ProductType.TROUSERS);
                    trousersEntity.setPhoto(entity.getPhoto());
                    entities.add(trousersEntity);
                    trousersEntity = trousersEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = new ArrayList<>();
        list.addAll(getAllTrousersEntities());
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
                productList.addAll(getAllTrousersEntities());
                break;
        }
        return productList;
    }

    public String addPhotoToTrousers(String hoodiePhoto){
        Map config = new HashMap();
        String cloudName, api_key, api_secret;
        cloudName = System.getenv("CLOUD_NAME");
        api_key = System.getenv("API_KEY");
        api_secret = System.getenv("API_SECRET");
        config.put("cloud_name", cloudName);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);
        List<TrousersEntity> list = new ArrayList<>();
        list.addAll(getAllTrousersEntities());
        Long trousersId = list.get(list.size()).getTrousersId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "trousers_"+trousersId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String trousers_id = "trousers_"+trousersId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(trousers_id);
        return url;
    }

    public void addToCart(Long productId, String productType){

    }

    public void deleteTrousersEntityById(Long id){
        trousersRepository.deleteTrousersEntityById(id);
    }
}
