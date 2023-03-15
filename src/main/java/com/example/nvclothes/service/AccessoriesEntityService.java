package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AccessoriesEntityService {

    private final AccessoriesEntityRepositoryInterface accessoriesRepository;



    public void createAccessoryEntity(@RequestBody AccessoriesDto accessoriesDto) throws Exception{
        Long count;
        if(accessoriesRepository.findAll().size()>0) count = accessoriesRepository.findAll().get(accessoriesRepository.findAll().size()-1).getAccessoriesId();
        else count = 0L;
        if (accessoriesDto.getCost() != null && accessoriesDto.getBrand()!=null
                && accessoriesDto.getName()!=null && accessoriesDto.getAmount()!=null){
            AccessoriesEntity accessoriesEntity = AccessoriesEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(accessoriesDto.getBrand()).build();

            accessoriesEntity.setAccessoriesId(++count);
            accessoriesEntity.setAttribute(Attribute.NAME.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getName());
            accessoriesEntity.setPhoto(addPhotoToAccessory(accessoriesDto.getPhoto()));
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.COST.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getCost().toString());
            accessoriesEntity.setPhoto(addPhotoToAccessory(accessoriesDto.getPhoto()));
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getAmount().toString());
            accessoriesEntity.setPhoto(addPhotoToAccessory(accessoriesDto.getPhoto()));
            accessoriesRepository.save(accessoriesEntity);

        }
        else {
            throw new Exception();
        }

    }

    public AccessoriesEntity getAccessoriesEntityById(Long id){
        return accessoriesRepository.getAccessoriesEntitiesById(id).get();
    }

    public List<AccessoriesEntity> getAccessoriesEntitiesByBrand(String brand){
        return  accessoriesRepository.getAccessoriesEntitiesByBrand(brand);
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
                    accessoriesEntity.setAccessoriesId(entity.getAccessoriesId());
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

    public List<Product> filter(List<Product> searchedList, String size,
                                String costFrom, String costTo, String brand, String productType){
        ListIterator<Product> iterator = searchedList.listIterator();
        String sizeE, productTypeE;
        Long costF, costT, costP;
        while (iterator.hasNext()){
            Product product = iterator.next();

            costP = product.getCost();
            costF = Long.parseLong(costFrom);
            costT = Long.parseLong(costTo);
            productTypeE = product.getProductType().getDisplayName();
            if (!((costF<=costP && costP<=costT) || (costT<=costP && costP<=costF) /*|| costF.equals("All")*/ &&
                (product.getBrand().getDisplayName().equals(brand) || brand.equals("All")))){
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
                productList.addAll(getAllAccessoriesEntities());
                break;
        }
        return productList;
    }

    public String addPhotoToAccessory(String hoodiePhoto){
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
        Long accessoryId = list.get(list.size()).getAccessoriesId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "accessory_"+accessoryId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String accessory_id = "accessory_"+accessoryId;
        String url = cloudinary.url().transformation(new Transformation().width(250).height(380).crop("fill")).generate(accessory_id);
        return url;
    }



    public void addToCart(Long productId, String productType){

    }


    public void deleteAccessoryEntityById(Long id){
        accessoriesRepository.deleteAccessoriesEntityById(id);
    }
}
