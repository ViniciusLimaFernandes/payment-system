package com.microservice.crud.application.service;

import com.microservice.crud.application.data.ProductVO;
import com.microservice.crud.application.entity.Product;
import com.microservice.crud.application.exceptions.ResourceNotFoundException;
import com.microservice.crud.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductVO create(ProductVO productVO) {
        return ProductVO.create(productRepository.save(Product.create(productVO)));
    }

    public Page<ProductVO> findAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);

        return page.map(this::convertToProductVO);
    }

    public ProductVO findByID(Long ID){
        return ProductVO.create(productRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID")));
    }

    public ProductVO update(ProductVO productVO){
        final Optional<Product> optionalProduct = productRepository.findById(productVO.getId());

        if (!optionalProduct.isPresent()){
            throw  new ResourceNotFoundException("No Records found for this ID");
        }

        return ProductVO.create(productRepository.save(Product.create(productVO)));
    }

    public void delete(Long ID){
        Product entity = productRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        productRepository.delete(entity);
    }

    private ProductVO convertToProductVO(Product product){
        return ProductVO.create(product);
    }
}
