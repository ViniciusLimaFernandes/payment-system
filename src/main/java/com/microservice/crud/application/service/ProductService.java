package com.microservice.crud.application.service;

import com.microservice.crud.application.data.ProductVO;
import com.microservice.crud.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductVO create(ProductVO productVO){

        return null;
    }

}
