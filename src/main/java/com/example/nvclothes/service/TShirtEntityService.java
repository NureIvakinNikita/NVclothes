package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TShirtEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.exception.TShirtNotFoundException;
import com.example.nvclothes.exception.TrousersNotFoundException;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.TShirtEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TShirtEntityService {
    private final TShirtEntityRepositoryInterface tShirtRepository;

    public void createHoodieEntity(@RequestBody TrainersDto trainersDto) throws Exception{

        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TShirtEntity tShirtEntity = TShirtEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trainersDto.getBrand()).build();
            tShirtEntity.setAttribute(Attribute.NAME.getDisplayName());
            tShirtEntity.setValue(trainersDto.getName());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.COST.getDisplayName());
            tShirtEntity.setValue(trainersDto.getCost().toString());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.SIZE.getDisplayName());
            tShirtEntity.setValue(trainersDto.getSize().toString());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            tShirtEntity.setValue(trainersDto.getAmount().toString());
            tShirtRepository.save(tShirtEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TShirtEntity getTShirtEntityById(Long id) throws TShirtNotFoundException{
        List<TShirtEntity> tShirt = tShirtRepository.getTShirtEntitiesByProductId(id);
        TShirtEntity trousersEntity = TShirtEntity.builder().build();
        Long numericValues;
        if (tShirt.size() == 0) {
            throw new TShirtNotFoundException("T-shirt not found for id: " + id);
        } else {
            for (TShirtEntity entity : tShirt) {
                switch (entity.getAttribute()) {
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
                        trousersEntity.setProductId(entity.getProductId());
                        trousersEntity.setProductType(ProductType.TSHIRT);
                        trousersEntity.setPhoto(entity.getPhoto());
                        return trousersEntity;
                    default:
                        break;
                }

            }
        }
        throw new TShirtNotFoundException("T-shirt not found for id: " + id);
    }

    public List<TShirtEntity> getTShirtEntitiesByBrand(String brand) throws TShirtNotFoundException{
        List<TShirtEntity> tShirts = new ArrayList<>();
        tShirts.addAll(tShirtRepository.getTShirtEntitiesByBrand(brand));
        if (tShirts.size() == 0) {
            throw new TShirtNotFoundException("T-shirt not found for brand: " + brand);
        }

        return tShirts;
    }

    public List<TShirtEntity> getAllTShirtEntities(){
        List<TShirtEntity> list = tShirtRepository.findAll();
        Comparator<TShirtEntity> byId = Comparator.comparing(TShirtEntity::getId);
        Collections.sort(list, byId);
        List<TShirtEntity> entities = new ArrayList<>();
        TShirtEntity tShirtEntity = TShirtEntity.builder().build();
        Long numericValues;
        for (TShirtEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    tShirtEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    tShirtEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    tShirtEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    tShirtEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    tShirtEntity.setAmount(numericValues);
                    tShirtEntity.setId(entity.getId());
                    tShirtEntity.setProductId(entity.getProductId());
                    tShirtEntity.setProductType(ProductType.TSHIRT);
                    tShirtEntity.setPhoto(entity.getPhoto());
                    entities.add(tShirtEntity);
                    tShirtEntity = tShirtEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = new ArrayList<>();
        list.addAll(getAllTShirtEntities());
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
            if (costFrom.equals("")) costFrom="0";
            if (costTo.equals("")) costTo="0";
            costP = product.getCost();
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
                productList.addAll(getAllTShirtEntities());
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
        List<TShirtEntity> list = new ArrayList<>();
        list.addAll(getAllTShirtEntities());
        Long tShirtId = list.get(list.size()).getProductId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "t_shirt_"+tShirtId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String t_shirt_id = "t_shirt_"+tShirtId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(t_shirt_id);
        return url;
    }

    public void addToCart(Long productId, String productType){

    }

    public void deleteTShirtEntityById(Long id){
        tShirtRepository.deleteTShirtEntityById(id);
    }
}
