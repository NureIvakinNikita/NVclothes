package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.TrousersDto;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.exception.TrousersNotFoundException;
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

    public void createTrousersEntity(@RequestBody TrousersDto trousersDto) throws Exception{

        if (trousersDto.getCost() != null && trousersDto.getBrand()!=null
                && trousersDto.getName()!=null && trousersDto.getAmount()!=null){
            TrousersEntity trousersEntity = TrousersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trousersDto.getBrand())
                    .photo(trousersDto.getPhoto())
                    .productId(trousersDto.getTrousersId())
                    .id(trousersDto.getId()).build();

            trousersRepository.save(trousersEntity);

            trousersEntity = TrousersEntity.builder()
                    .productId(trousersDto.getTrousersId()).build();
            trousersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trousersEntity.setValue(trousersDto.getName());
            trousersEntity.setPhoto(trousersDto.getPhoto());
            trousersRepository.save(trousersEntity);

            trousersEntity = TrousersEntity.builder()
                    .productId(trousersDto.getTrousersId()).build();

            trousersEntity.setAttribute(Attribute.COST.getDisplayName());
            trousersEntity.setValue(trousersDto.getCost().toString());
            trousersEntity.setPhoto(trousersDto.getPhoto());
            trousersRepository.save(trousersEntity);

            trousersEntity = TrousersEntity.builder()
                    .productId(trousersDto.getTrousersId()).build();
            trousersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trousersEntity.setValue(trousersDto.getSize().getDisplayName());
            trousersEntity.setPhoto(trousersDto.getPhoto());
            trousersRepository.save(trousersEntity);

            trousersEntity = TrousersEntity.builder()
                    .productId(trousersDto.getTrousersId()).build();
            trousersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trousersEntity.setValue(trousersDto.getAmount().toString());
            trousersEntity.setPhoto(trousersDto.getPhoto());
            trousersRepository.save(trousersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrousersEntity getTrousersEntityById(Long id) throws TrousersNotFoundException{
        List<TrousersEntity> trousers = trousersRepository.getTrousersEntitiesByProductId(id);
        TrousersEntity trousersEntity = TrousersEntity.builder().build();
        Comparator<TrousersEntity> byId = Comparator.comparing(TrousersEntity::getId);
        Collections.sort(trousers, byId);
        Long numericValues;
        if (trousers.size() == 0) {
            throw new TrousersNotFoundException("Trousers not found for id: " + id);
        } else {
            for (TrousersEntity entity : trousers) {
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
                        trousersEntity.setProductType(ProductType.TROUSERS);
                        trousersEntity.setPhoto(entity.getPhoto());
                        return trousersEntity;
                    default:
                        break;
                }

            }
        }
        throw new TrousersNotFoundException("Trousers not found for id: " + id);
    }

    public List<TrousersEntity> getTrousersEntitiesByBrand(String brand) throws TrousersNotFoundException{
        List<TrousersEntity> trousers = new ArrayList<>();
        trousers.addAll(trousersRepository.getTrousersEntitiesByBrand(brand));
        if (trousers.size() == 0) {
            throw new TrousersNotFoundException("Trousers not found for brand: " + brand);
        }

        return trousers;
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
                    trousersEntity.setProductId(entity.getProductId());
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

    public List<Product> filter(List<Product> searchedList, FilterObject filterObject/*String size,
                                String costFrom, String costTo, String brand, String productType*/){
        /*= searchedList.listIterator();*/
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(this.getAllTrousersEntities());
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
        Long trousersId = list.get(list.size()).getProductId();
        try {
            cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "trousers_"+trousersId));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String trousers_id = "trousers_"+trousersId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(trousers_id);
        return url;
    }

    public void save(TrousersEntity trousers){

        Optional<TrousersEntity> optionalEntity = trousersRepository.getTrousersEntityByProductIdAndAttribute(trousers.getProductId(), "BRAND");
        if (optionalEntity.isPresent()) {
            TrousersEntity entity = optionalEntity.get();
            entity.setAttribute("BRAND");
            entity.setValue(trousers.getBrand().getDisplayName());
            entity.setProductId(trousers.getProductId());
            entity.setPhoto(trousers.getPhoto());
            trousersRepository.save(entity);
        }


        optionalEntity = trousersRepository.getTrousersEntityByProductIdAndAttribute(trousers.getProductId(), "NAME");
        if (optionalEntity.isPresent()) {
            TrousersEntity entity = optionalEntity.get();
            entity.setAttribute("NAME");
            entity.setValue(trousers.getName());
            entity.setProductId(trousers.getProductId());
            trousersRepository.save(entity);
        }


        optionalEntity = trousersRepository.getTrousersEntityByProductIdAndAttribute(trousers.getProductId(), "COST");
        if (optionalEntity.isPresent()) {
            TrousersEntity entity = optionalEntity.get();
            entity.setAttribute("COST");
            entity.setValue(trousers.getCost().toString());
            entity.setProductId(trousers.getProductId());
            trousersRepository.save(entity);
        }

        optionalEntity = trousersRepository.getTrousersEntityByProductIdAndAttribute(trousers.getProductId(), "SIZE");
        if (optionalEntity.isPresent()) {
            TrousersEntity entity = optionalEntity.get();
            entity.setAttribute("SIZE");
            entity.setValue(trousers.getSize().getDisplayName());
            entity.setProductId(trousers.getProductId());
            trousersRepository.save(entity);
        }

        optionalEntity = trousersRepository.getTrousersEntityByProductIdAndAttribute(trousers.getProductId(), "AMOUNT");
        if (optionalEntity.isPresent()) {
            TrousersEntity entity = optionalEntity.get();
            entity.setAttribute("AMOUNT");
            entity.setValue(trousers.getAmount().toString());
            entity.setProductId(trousers.getProductId());
            trousersRepository.save(entity);
        }

    }
    public void addToCart(Long productId, String productType){

    }

    public void deleteTrousersEntitiesByProductId(Long productId){
        trousersRepository.deleteTrousersEntitiesByProductId(productId);
    }

    public void deleteTrousersEntityById(Long id){
        trousersRepository.deleteTrousersEntityById(id);
    }
}
