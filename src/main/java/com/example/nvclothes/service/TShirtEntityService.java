package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.TShirtDto;
import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TShirtEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.exception.TShirtNotFoundException;
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

    public void createTShirtEntity(@RequestBody TShirtDto tShirtDto) throws Exception{

        if (tShirtDto.getCost() != null && tShirtDto.getBrand()!=null
                && tShirtDto.getName()!=null && tShirtDto.getAmount()!=null){
            TShirtEntity tShirtEntity = TShirtEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(tShirtDto.getBrand())
                    .photo(tShirtDto.getPhoto())
                    .productId(tShirtDto.getTShirtId())
                    .id(tShirtDto.getId()).build();

            tShirtRepository.save(tShirtEntity);

            tShirtEntity = TShirtEntity.builder()
                            .productId(tShirtDto.getTShirtId()).build();
            tShirtEntity.setAttribute(Attribute.NAME.getDisplayName());
            tShirtEntity.setValue(tShirtDto.getName());
            tShirtEntity.setPhoto(tShirtDto.getPhoto());
            tShirtRepository.save(tShirtEntity);


            tShirtEntity = TShirtEntity.builder()
                    .productId(tShirtDto.getTShirtId()).build();
            tShirtEntity.setAttribute(Attribute.COST.getDisplayName());
            tShirtEntity.setValue(tShirtDto.getCost().toString());
            tShirtEntity.setPhoto(tShirtDto.getPhoto());
            tShirtRepository.save(tShirtEntity);

            tShirtEntity = TShirtEntity.builder()
                    .productId(tShirtDto.getTShirtId()).build();
            tShirtEntity.setAttribute(Attribute.SIZE.getDisplayName());
            tShirtEntity.setValue(tShirtDto.getSize().getDisplayName());
            tShirtEntity.setPhoto(tShirtDto.getPhoto());
            tShirtRepository.save(tShirtEntity);

            tShirtEntity = TShirtEntity.builder()
                    .productId(tShirtDto.getTShirtId()).build();
            tShirtEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            tShirtEntity.setValue(tShirtDto.getAmount().toString());
            tShirtEntity.setPhoto(tShirtDto.getPhoto());
            tShirtRepository.save(tShirtEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TShirtEntity getTShirtEntityById(Long id) throws TShirtNotFoundException{
        List<TShirtEntity> tShirt = tShirtRepository.getTShirtEntitiesByProductId(id);
        TShirtEntity tShirtEntity = TShirtEntity.builder().build();
        Comparator<TShirtEntity> byId = Comparator.comparing(TShirtEntity::getId);
        Collections.sort(tShirt, byId);
        Long numericValues;
        if (tShirt.size() == 0) {
            throw new TShirtNotFoundException("T-shirt not found for id: " + id);
        } else {
            for (TShirtEntity entity : tShirt) {
                switch (entity.getAttribute()) {
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
                        return tShirtEntity;
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

    public List<Product> filter(List<Product> searchedList, FilterObject filterObject/*String size,
                                String costFrom, String costTo, String brand, String productType*/){
        /*= searchedList.listIterator();*/
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(this.getAllTShirtEntities());
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
                //iterator.remove();
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

    public void save(TShirtEntity tShirt){

        Optional<TShirtEntity> optionalEntity = tShirtRepository.getTShirtEntityByProductIdAndAttribute(tShirt.getProductId(), "BRAND");
        if (optionalEntity.isPresent()) {
            TShirtEntity entity = optionalEntity.get();
            entity.setAttribute("BRAND");
            entity.setValue(tShirt.getBrand().getDisplayName());
            entity.setProductId(tShirt.getProductId());
            entity.setPhoto(tShirt.getPhoto());
            tShirtRepository.save(entity);
        }


        optionalEntity = tShirtRepository.getTShirtEntityByProductIdAndAttribute(tShirt.getProductId(), "NAME");
        if (optionalEntity.isPresent()) {
            TShirtEntity entity = optionalEntity.get();
            entity.setAttribute("NAME");
            entity.setValue(tShirt.getName());
            entity.setProductId(tShirt.getProductId());
            tShirtRepository.save(entity);
        }


        optionalEntity = tShirtRepository.getTShirtEntityByProductIdAndAttribute(tShirt.getProductId(), "COST");
        if (optionalEntity.isPresent()) {
            TShirtEntity entity = optionalEntity.get();
            entity.setAttribute("COST");
            entity.setValue(tShirt.getCost().toString());
            entity.setProductId(tShirt.getProductId());
            tShirtRepository.save(entity);
        }

        optionalEntity = tShirtRepository.getTShirtEntityByProductIdAndAttribute(tShirt.getProductId(), "SIZE");
        if (optionalEntity.isPresent()) {
            TShirtEntity entity = optionalEntity.get();
            entity.setAttribute("SIZE");
            entity.setValue(tShirt.getSize().getDisplayName());
            entity.setProductId(tShirt.getProductId());
            tShirtRepository.save(entity);
        }

        optionalEntity = tShirtRepository.getTShirtEntityByProductIdAndAttribute(tShirt.getProductId(), "AMOUNT");
        if (optionalEntity.isPresent()) {
            TShirtEntity entity = optionalEntity.get();
            entity.setAttribute("AMOUNT");
            entity.setValue(tShirt.getAmount().toString());
            entity.setProductId(tShirt.getProductId());
            tShirtRepository.save(entity);
        }

    }

    public void addToCart(Long productId, String productType){

    }

    public void deleteTShirtEntitiesByProductId(Long productId){
        tShirtRepository.deleteTShirtEntitiesByProductId(productId);
    }
    public void deleteTShirtEntityById(Long id){
        tShirtRepository.deleteTShirtEntityById(id);
    }
}
