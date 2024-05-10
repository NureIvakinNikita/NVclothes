package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.AccessoryNotFoundException;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AccessoriesEntityService implements IAccessoriesEntityService {

    private final AccessoriesEntityRepositoryInterface accessoriesRepository;



    public void createAccessoryEntity(@RequestBody AccessoriesDto accessoriesDto) throws Exception{
        if (accessoriesDto.getCost() != null && accessoriesDto.getBrand()!=null
                && accessoriesDto.getName()!=null && accessoriesDto.getAmount()!=null){
            AccessoriesEntity accessoriesEntity = AccessoriesEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(accessoriesDto.getBrand())
                    .photo(accessoriesDto.getPhoto())
                    .productId(accessoriesDto.getAccessoriesId())
                    .id(accessoriesDto.getId()).build();

            accessoriesRepository.save(accessoriesEntity);

            accessoriesEntity = AccessoriesEntity.builder()
                    .productId(accessoriesDto.getAccessoriesId()).build();
            accessoriesEntity.setAttribute(Attribute.NAME.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getName());
            accessoriesEntity.setPhoto(accessoriesDto.getPhoto());
            accessoriesRepository.save(accessoriesEntity);


            accessoriesEntity = AccessoriesEntity.builder()
                    .productId(accessoriesDto.getAccessoriesId()).build();
            accessoriesEntity.setAttribute(Attribute.COST.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getCost().toString());
            accessoriesEntity.setPhoto(accessoriesDto.getPhoto());
            accessoriesRepository.save(accessoriesEntity);


            accessoriesEntity = AccessoriesEntity.builder()
                    .productId(accessoriesDto.getAccessoriesId()).build();
            accessoriesEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getAmount().toString());
            accessoriesEntity.setPhoto(accessoriesDto.getPhoto());
            accessoriesRepository.save(accessoriesEntity);
        }
        else {
            throw new Exception();
        }
    }

    public AccessoriesEntity getAccessoryEntityById(Long id) throws AccessoryNotFoundException{
        List<AccessoriesEntity> accessory = accessoriesRepository.getAccessoriesEntitiesByProductId(id);
        Comparator<AccessoriesEntity> byId = Comparator.comparing(AccessoriesEntity::getId);
        Collections.sort(accessory, byId);
        AccessoriesEntity accessoryEntity = AccessoriesEntity.builder().build();
        Long numericValues;
        if (accessory.size() == 0) {
            throw new AccessoryNotFoundException("Accessory not found for id: " + id);
        } else {
            for (AccessoriesEntity entity : accessory) {
                switch (entity.getAttribute()) {
                    case "BRAND":
                        accessoryEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                        break;
                    case "NAME":
                        accessoryEntity.setName(entity.getValue());
                        break;
                    case "COST":
                        numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                        accessoryEntity.setCost(numericValues);
                        break;
                    case "AMOUNT":
                        numericValues = Long.parseLong(entity.getValue());
                        accessoryEntity.setAmount(numericValues);
                        accessoryEntity.setId(entity.getId());
                        accessoryEntity.setProductId(entity.getProductId());
                        accessoryEntity.setProductType(ProductType.ACCESSORY);
                        accessoryEntity.setPhoto(entity.getPhoto());
                        return accessoryEntity;
                    default:
                        break;
                }

            }
        }
        throw new AccessoryNotFoundException("Accessory not found for id: " + id);
    }

    public List<AccessoriesEntity> getAccessoriesEntitiesByBrand(String brand) throws AccessoryNotFoundException{
        List<AccessoriesEntity> accessories = new ArrayList<>();
        accessories.addAll(accessoriesRepository.getAccessoriesEntitiesByBrand(brand));
        if (accessories.size() == 0) {
            throw new AccessoryNotFoundException("Accessories not found for brand: " + brand);
        }

        return accessories;
    }

    public List<AccessoriesEntity> getAllAccessoriesEntities(){
        List<AccessoriesEntity> list = accessoriesRepository.findAll();
        Comparator<AccessoriesEntity> byId = Comparator.comparing(AccessoriesEntity::getId);
        Collections.sort(list, byId);
        List<AccessoriesEntity> entities = new ArrayList<>();
        AccessoriesEntity accessoriesEntity = AccessoriesEntity.builder().build();
        Long numericValues;
        for (AccessoriesEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    accessoriesEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    String brandString = Brand.fromDisplayName(entity.getValue()).getDisplayName();
                    break;
                case "NAME":
                    accessoriesEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    accessoriesEntity.setCost(numericValues);
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    accessoriesEntity.setAmount(numericValues);
                    accessoriesEntity.setId(entity.getId());
                    accessoriesEntity.setProductId(entity.getProductId());
                    accessoriesEntity.setProductType(ProductType.ACCESSORY);
                    accessoriesEntity.setPhoto(entity.getPhoto());
                    entities.add(accessoriesEntity);

                    accessoriesEntity = AccessoriesEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = new ArrayList<>();
        list.addAll(getAllAccessoriesEntities());
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
        allProducts.addAll(this.getAllAccessoriesEntities());
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
                    (product.getBrand().getDisplayName().equals(filterObject.getBrand()) || filterObject.getBrand().equals("All")))){
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
                productList.addAll(getAllAccessoriesEntities());
                break;
        }
        return productList;
    }

    public String addPhotoToAccessory(String accessoryPhoto){
        Map config = new HashMap();
        String cloudName, api_key, api_secret;
        cloudName = System.getenv("CLOUD_NAME");
        api_key = System.getenv("API_KEY");
        api_secret = System.getenv("API_SECRET");
        config.put("cloud_name", cloudName);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);
        List<AccessoriesEntity> list = new ArrayList<>();
        list.addAll(getAllAccessoriesEntities());
        Long accessoryId = list.get(list.size()).getProductId();
        try {
            cloudinary.uploader().upload(accessoryPhoto, ObjectUtils.asMap("public_id", "accessory_"+accessoryId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String accessory_id = "accessory_"+accessoryId;
        String url = cloudinary.url().transformation(new Transformation().width(250).height(380).crop("fill")).generate(accessory_id);
        return url;
    }


    public void save(AccessoriesEntity accessories){
        Optional<AccessoriesEntity> optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(accessories.getProductId(), "BRAND");
        if (optionalEntity.isPresent()) {
            AccessoriesEntity entity = optionalEntity.get();
            entity.setAttribute("BRAND");
            entity.setValue(accessories.getBrand().getDisplayName());
            entity.setProductId(accessories.getProductId());
            entity.setPhoto(accessories.getPhoto());
            accessoriesRepository.save(entity);
        }


        optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(accessories.getProductId(), "NAME");
        if (optionalEntity.isPresent()) {
            AccessoriesEntity entity = optionalEntity.get();
            entity.setAttribute("NAME");
            entity.setValue(accessories.getName());
            entity.setProductId(accessories.getProductId());
            accessoriesRepository.save(entity);
        }


        optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(accessories.getProductId(), "COST");
        if (optionalEntity.isPresent()) {
            AccessoriesEntity entity = optionalEntity.get();
            entity.setAttribute("COST");
            entity.setValue(accessories.getCost().toString());
            entity.setProductId(accessories.getProductId());
            accessoriesRepository.save(entity);
        }


        optionalEntity = accessoriesRepository.getAccessoriesEntityByProductIdAndAttribute(accessories.getProductId(), "AMOUNT");
        if (optionalEntity.isPresent()) {
            AccessoriesEntity entity = optionalEntity.get();
            entity.setAttribute("AMOUNT");
            entity.setValue(accessories.getAmount().toString());
            entity.setProductId(accessories.getProductId());
            accessoriesRepository.save(entity);
        }

    }


    public void addToCart(Long productId, String productType){

    }

    public void deleteAccessoriesEntitiesByProductId(Long productId){
        accessoriesRepository.deleteAccessoriesEntitiesByProductId(productId);
    }
    public void deleteAccessoryEntityById(Long id){
        accessoriesRepository.deleteAccessoriesEntityById(id);
    }
}
