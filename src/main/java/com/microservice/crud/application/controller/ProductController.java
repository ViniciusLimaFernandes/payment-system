package com.microservice.crud.application.controller;

import com.microservice.crud.application.data.ProductVO;
import com.microservice.crud.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/product")
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

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAll (@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "limit", defaultValue = "12") int limit,
                             @RequestParam(value = "direction", defaultValue = "asc") String direction){

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));

        Page<ProductVO> products = productService.findAll(pageable);

        products.stream().forEach(p -> p.add(linkTo(methodOn(ProductController.class).findByID(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProductVO>> pagedModel = assembler.toModel(products);

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ProductVO create(@RequestBody ProductVO productVO){
        ProductVO productVORes = productService.create(productVO);

        productVORes.add(linkTo(methodOn(ProductController.class).findByID(productVO.getId())).withSelfRel());

        return productVORes;
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ProductVO update(@RequestBody ProductVO productVO){
        ProductVO productVORes = productService.update(productVO);

        productVORes.add(linkTo(methodOn(ProductController.class).findByID(productVO.getId())).withSelfRel());

        return productVORes;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
