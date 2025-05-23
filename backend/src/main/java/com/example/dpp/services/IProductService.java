package com.example.dpp.services;

import com.example.dpp.model.api.products.ProductCreation;
import com.example.dpp.model.api.products.ProductInfo;

import java.util.List;

public interface IProductService {

    List<ProductInfo> getProducts();

    ProductInfo getProduct(int id);

    ProductInfo createProduct(ProductCreation product);

    ProductInfo updateProduct(ProductInfo product);

    ProductInfo updateProduct(int id, ProductCreation product);

    boolean deleteProduct(int id);
}
