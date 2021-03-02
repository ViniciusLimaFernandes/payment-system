package com.microservice.crud.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.crud.application.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("price")
    private Double price;

    public static ProductVO create(Product product) {
        return new ModelMapper()
                .map(product, ProductVO.class);
    }
}
