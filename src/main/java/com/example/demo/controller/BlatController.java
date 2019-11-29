package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlatController {
    private ProductRepo productRepo;

    @RequestMapping()
    public String blat(Model model){
        model.addAttribute("products", productRepo.findAll());
        return "index";
    }

    @RequestMapping("add_product")
    public String blatAdd(){
        return "add_product";
    }

    @RequestMapping(value = "add_product", method = RequestMethod.POST)
    public String blatAddSuka(Model model, @RequestParam String name, @RequestParam Double price){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreated(new Date());
        productRepo.save(product);
        model.addAttribute("products", productRepo.findAll());
        return "index";
    }

    @RequestMapping(value = "delete")
    public String blatDelete(Model model, @RequestParam Long id){
        productRepo.deleteById(id);
        model.addAttribute("products", productRepo.findAll());
        return "index";
    }

    @RequestMapping(value = "filter")
    public String blatFindSuka(Model model, @RequestParam String key){
        List<Product> products = productRepo.findAll().stream()
                .filter(product -> product.getName().contains(key))
                .collect(Collectors.toList());
        model.addAttribute("key", key);
        model.addAttribute("products", products);
        return "index";
    }

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
}
