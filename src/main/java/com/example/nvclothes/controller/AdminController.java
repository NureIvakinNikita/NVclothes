package com.example.nvclothes.controller;

import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.*;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.service.AdminService;
import com.example.nvclothes.service.AllProductsServices;
import com.example.nvclothes.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private AllProductsServices allProductsServices;

    @Autowired
    private FilterObject filterObject;

    @GetMapping("/admin/product/{id}")
    @ResponseBody
    public String getProductById(@PathVariable Long id) {
        AccessoriesEntity accessoriesEntity = (AccessoriesEntity) allProductsServices.getProductByProductIdAndType(id, "ACCESSORY");
        return accessoriesEntity.toString();
    }


    @GetMapping("/admin/add/product")
    public ModelAndView add(){
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        List<Brand> brandList = Arrays.stream(Brand.values()).toList();
        List<Size> sizeList = Arrays.stream(Size.values()).toList();
        List<ProductType> typeList = Arrays.stream(ProductType.values()).toList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addProduct");
        modelAndView.addObject("role",role);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("brandList", brandList);
        modelAndView.addObject("sizeList", sizeList);
        modelAndView.addObject("typeList", typeList);
        return  modelAndView;
    }
    @GetMapping("/admin/edit")
    public ModelAndView editPage(@RequestParam("productIdEdit") Long productIdEdit,
                                 @RequestParam("productTypeEdit") String productTypeEdit){
        ModelAndView modelAndView = new ModelAndView();

        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        Product product = null;
        switch (productTypeEdit) {
            case "HOODIE":
                product = allProductsServices.getProductByProductIdAndType(productIdEdit, productTypeEdit);
                modelAndView.setViewName("editHoodie");
                modelAndView.addObject("product", product);
                break;
            case "ACCESSORY":
                product = allProductsServices.getProductByProductIdAndType(productIdEdit, productTypeEdit);
                modelAndView.setViewName("editAccessory");
                modelAndView.addObject("product", product);
                break;
            case "TRAINERS":
                product = allProductsServices.getProductByProductIdAndType(productIdEdit, productTypeEdit);
                modelAndView.setViewName("editTrainers");
                modelAndView.addObject("product", product);
                break;
            case "TROUSERS":
                product = allProductsServices.getProductByProductIdAndType(productIdEdit, productTypeEdit);
                modelAndView.setViewName("editTrousers");
                modelAndView.addObject("product", product);
                break;
            case "TSHIRT":
                product = allProductsServices.getProductByProductIdAndType(productIdEdit, productTypeEdit);
                modelAndView.setViewName("editTShirts");
                modelAndView.addObject("product", product);
                break;
            default:
                RedirectView redirectView = new RedirectView("/all-products");
                ModelAndView modelAndViewRedirect = new ModelAndView(redirectView);
                return modelAndViewRedirect;
        }
        List<Brand> brandList = Arrays.stream(Brand.values()).toList();
        modelAndView.addObject("role", role);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("product", product);
        modelAndView.addObject("brandList", brandList);
        return modelAndView;
    }

    @GetMapping("/admin/delete")
    public ModelAndView deletePage(@RequestParam("productIdDelete") Long productIdDelete,
                                 @RequestParam("productTypeDelete") String productTypeDelete){
        ModelAndView modelAndView = new ModelAndView();

        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        Product product = null;
        modelAndView.setViewName("deletePage");
        switch (productTypeDelete) {
            case "HOODIE":
                product = allProductsServices.getProductByProductIdAndType(productIdDelete, productTypeDelete);
                //modelAndView.addObject("product", product);
                break;
            case "ACCESSORY":
                product = allProductsServices.getProductByProductIdAndType(productIdDelete, productTypeDelete);
                //modelAndView.setViewName("deletePage");
                //modelAndView.addObject("product", product);
                break;
            case "TRAINERS":
                product = allProductsServices.getProductByProductIdAndType(productIdDelete, productTypeDelete);
                //modelAndView.setViewName("deletePage");
                ///modelAndView.addObject("product", product);
                break;
            case "TROUSERS":
                product = allProductsServices.getProductByProductIdAndType(productIdDelete, productTypeDelete);
                //modelAndView.setViewName("deletePage");
                //modelAndView.addObject("product", product);
                break;
            case "TSHIRT":
                product = allProductsServices.getProductByProductIdAndType(productIdDelete, productTypeDelete);
                //modelAndView.setViewName("deletePage");
                //modelAndView.addObject("product", product);
                break;
            default:
                RedirectView redirectView = new RedirectView("/all-products");
                ModelAndView modelAndViewRedirect = new ModelAndView(redirectView);
                return modelAndViewRedirect;
        }
        List<Brand> brandList = Arrays.stream(Brand.values()).toList();
        modelAndView.addObject("role", role);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("product", product);

        return modelAndView;
    }

    @GetMapping("/admin/canceled")
    public ModelAndView cancel(){
        RedirectView redirectView = new RedirectView("/all-products");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }

    @PostMapping("/admin/add/product")
    public ModelAndView add(@RequestParam("photo") String photo, @RequestParam("brand") String brand,
                            @RequestParam("name") String name, @RequestParam("cost") Long cost,
                            @RequestParam("size") String size, @RequestParam("amount") Long amount,
                            @RequestParam("type") String type){

        adminService.addProduct(photo, brand, name, cost, size, amount, type);

        RedirectView redirectView = new RedirectView("/all-products");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }

    @PostMapping("/admin/edit")
    public ModelAndView edit(@RequestParam("productId") Long productId, @RequestParam("productType") String productType,
                            @RequestParam("brand") String brand, @RequestParam("name") String name,
                             @RequestParam("cost") Long cost, @RequestParam("amount") Long amount,
                             @RequestParam("size") String size, @RequestParam("photo") String photo) {

        boolean flag =  adminService.edit(productId, productType, brand, name, cost, amount, size);
        if (flag) {
            Product product = allProductsServices.getProductByProductIdAndType(productId, productType);
        }

        RedirectView redirectView = new RedirectView("/all-products");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }

    @PostMapping("/admin/delete")
    public ModelAndView edit(@RequestParam("productId") Long productId, @RequestParam("productType") String productType) {

        boolean flag =  adminService.delete(productId, productType);
        if (flag) {
            Product product = allProductsServices.getProductByProductIdAndType(productId, productType);
        }

        RedirectView redirectView = new RedirectView("/all-products");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }
}
