package com.example.nvclothes.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.dto.HoodieDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.HoodieEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TShirtEntity;
import com.example.nvclothes.exception.HoodieNotFoundException;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.HoodieEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HoodieEntityService {
    private final HoodieEntityRepositoryInterface hoodieRepository;

    public void createHoodieEntity(@RequestBody HoodieDto hoodieDto) throws Exception{


        if (hoodieDto.getCost() != null && hoodieDto.getBrand()!=null
                && hoodieDto.getName()!=null && hoodieDto.getAmount()!=null){
            HoodieEntity hoodieEntity = HoodieEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(hoodieDto.getBrand())
                    .photo(hoodieDto.getPhoto())
                    .productId(hoodieDto.getHoodieId())
                    .id(hoodieDto.getId()).build();

            hoodieRepository.save(hoodieEntity);

            hoodieEntity = HoodieEntity.builder()
                    .productId(hoodieDto.getHoodieId()).build();
            hoodieEntity.setAttribute(Attribute.NAME.getDisplayName());
            hoodieEntity.setValue(hoodieDto.getName());
            hoodieEntity.setPhoto(hoodieDto.getPhoto());
            hoodieRepository.save(hoodieEntity);


            hoodieEntity = HoodieEntity.builder()
                    .productId(hoodieDto.getHoodieId()).build();
            hoodieEntity.setAttribute(Attribute.COST.getDisplayName());
            hoodieEntity.setValue(hoodieDto.getCost().toString());
            hoodieEntity.setPhoto(hoodieDto.getPhoto());
            hoodieRepository.save(hoodieEntity);


            hoodieEntity = HoodieEntity.builder()
                    .productId(hoodieDto.getHoodieId()).build();
            hoodieEntity.setAttribute(Attribute.SIZE.getDisplayName());
            hoodieEntity.setValue(hoodieDto.getSize().getDisplayName());
            hoodieEntity.setPhoto(hoodieDto.getPhoto());
            hoodieRepository.save(hoodieEntity);

            hoodieEntity = HoodieEntity.builder()
                    .productId(hoodieDto.getHoodieId()).build();
            hoodieEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            hoodieEntity.setValue(hoodieDto.getAmount().toString());
            hoodieEntity.setPhoto(hoodieDto.getPhoto());
            hoodieRepository.save(hoodieEntity);
        }
        else {
            throw new Exception();
        }

    }

    public HoodieEntity getHoodieEntityById(Long id) throws HoodieNotFoundException{
        List<HoodieEntity> hoodie = hoodieRepository.getHoodieEntitiesByProductId(id);
        HoodieEntity hoodieEntity = HoodieEntity.builder().build();
        Comparator<HoodieEntity> byId = Comparator.comparing(HoodieEntity::getId);
        Collections.sort(hoodie, byId);
        Long numericValues;
        if (hoodie.size() == 0) {
            throw new HoodieNotFoundException("Hoodie not found for id: " + id);
        } else {
            for (HoodieEntity entity : hoodie) {
                switch (entity.getAttribute()) {
                    case "BRAND":
                        hoodieEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                        break;
                    case "NAME":
                        hoodieEntity.setName(entity.getValue());
                        break;
                    case "COST":
                        numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                        hoodieEntity.setCost(numericValues);
                        break;
                    case "SIZE":
                        hoodieEntity.setSize(Size.fromDisplayName(entity.getValue()));
                        break;
                    case "AMOUNT":
                        numericValues = Long.parseLong(entity.getValue());
                        hoodieEntity.setAmount(numericValues);
                        hoodieEntity.setId(entity.getId());
                        hoodieEntity.setProductId(entity.getProductId());
                        hoodieEntity.setProductType(ProductType.HOODIE);
                        hoodieEntity.setPhoto(entity.getPhoto());
                        return hoodieEntity;
                    default:
                        break;
                }

            }
        }
        throw new HoodieNotFoundException("Hoodie not found for id: " + id);
    }

    public List<HoodieEntity> getHoodieEntitiesByBrand(String brand) throws HoodieNotFoundException{
        List<HoodieEntity> hoodies = new ArrayList<>();
        hoodies.addAll(hoodieRepository.getHoodieEntitiesByBrand(brand));
        if (hoodies.size() == 0) {
            throw new HoodieNotFoundException("Hoodie not found for brand: " + brand);
        }

        return hoodies;
    }

    public List<HoodieEntity> getAllHoodieEntities(){
        List<HoodieEntity> list = hoodieRepository.findAll();
        Comparator<HoodieEntity> byId = Comparator.comparing(HoodieEntity::getId);
        Collections.sort(list, byId);
        List<HoodieEntity> entities = new ArrayList<>();
        HoodieEntity hoodieEntity = HoodieEntity.builder().build();
        Long numericValues;
        for (HoodieEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    hoodieEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    hoodieEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    hoodieEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    hoodieEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    hoodieEntity.setAmount(numericValues);
                    hoodieEntity.setId(entity.getId());
                    hoodieEntity.setProductId(entity.getProductId());
                    hoodieEntity.setProductType(ProductType.HOODIE);
                    hoodieEntity.setPhoto(entity.getPhoto());
                    entities.add(hoodieEntity);
                    hoodieEntity = HoodieEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public List<Product> searchProducts(String searchItem){
        List<Product> list = new ArrayList<>();
        list.addAll(getAllHoodieEntities());
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
        allProducts.addAll(this.getAllHoodieEntities());
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
                productList.addAll(getAllHoodieEntities());
                break;
        }
        return productList;
    }

    public String addPhotoToHoodie(String hoodiePhoto){
        Map config = new HashMap();
        String cloudName, api_key, api_secret;
        cloudName = System.getenv("CLOUD_NAME");
        api_key = System.getenv("API_KEY");
        api_secret = System.getenv("API_SECRET");
        config.put("cloud_name", cloudName);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);
        List<HoodieEntity> list = new ArrayList<>();
        list.addAll(getAllHoodieEntities());
        Long hoodieId = list.get(list.size()).getProductId();
		try {
			cloudinary.uploader().upload(hoodiePhoto, ObjectUtils.asMap("public_id", "hoodie_"+hoodieId));
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
        String hoodie_id = "hoodie_"+hoodieId;
        String url = cloudinary.url().transformation(new Transformation().width(255).height(380).crop("fill")).generate(hoodie_id);
        return url;
    }

    public void save(HoodieEntity hoodie){

        Optional<HoodieEntity> optionalEntity = hoodieRepository.getHoodieEntityByProductIdAndAttribute(hoodie.getProductId(), "BRAND");
        if (optionalEntity.isPresent()) {
            HoodieEntity entity = optionalEntity.get();
            entity.setAttribute("BRAND");
            entity.setValue(hoodie.getBrand().getDisplayName());
            entity.setProductId(hoodie.getProductId());
            entity.setPhoto(hoodie.getPhoto());
            hoodieRepository.save(entity);
        }


        optionalEntity = hoodieRepository.getHoodieEntityByProductIdAndAttribute(hoodie.getProductId(), "NAME");
        if (optionalEntity.isPresent()) {
            HoodieEntity entity = optionalEntity.get();
            entity.setAttribute("NAME");
            entity.setValue(hoodie.getName());
            entity.setProductId(hoodie.getProductId());
            hoodieRepository.save(entity);
        }


        optionalEntity = hoodieRepository.getHoodieEntityByProductIdAndAttribute(hoodie.getProductId(), "COST");
        if (optionalEntity.isPresent()) {
            HoodieEntity entity = optionalEntity.get();
            entity.setAttribute("COST");
            entity.setValue(hoodie.getCost().toString());
            entity.setProductId(hoodie.getProductId());
            hoodieRepository.save(entity);
        }

        optionalEntity = hoodieRepository.getHoodieEntityByProductIdAndAttribute(hoodie.getProductId(), "SIZE");
        if (optionalEntity.isPresent()) {
            HoodieEntity entity = optionalEntity.get();
            entity.setAttribute("SIZE");
            entity.setValue(hoodie.getSize().getDisplayName());
            entity.setProductId(hoodie.getProductId());
            hoodieRepository.save(entity);
        }

        optionalEntity = hoodieRepository.getHoodieEntityByProductIdAndAttribute(hoodie.getProductId(), "AMOUNT");
        if (optionalEntity.isPresent()) {
            HoodieEntity entity = optionalEntity.get();
            entity.setAttribute("AMOUNT");
            entity.setValue(hoodie.getAmount().toString());
            entity.setProductId(hoodie.getProductId());
            hoodieRepository.save(entity);
        }

    }

    public void deleteHoodieEntitiesByProductId(Long id){
        hoodieRepository.deleteHoodieEntitiesByProductId(id);
    }

    public void addToCart(Long productId, String productType){

    }

    public void deleteHoodieEntityById(Long id){
        hoodieRepository.deleteHoodieEntityById(id);
    }
}
