package com.microservice.crud.application.controller;

import com.microservice.crud.application.data.ProductVO;
import com.microservice.crud.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/produto")
public class ProductController  {

    @Autowired
    ProductService productService;

    @Autowired
    PagedResourcesAssembler<ProductVO> assembler;

    @GetMapping(value = "/{id}", produces = "application/json")
    public  ProductVO findByID (@PathVariable("id") Long id){
        ProductVO productVO = productService.findByID(id);
        productVO.add(linkTo(methodOn(ProductController.class).findByID(id)).withSelfRel());
        return productVO;
    }
}
