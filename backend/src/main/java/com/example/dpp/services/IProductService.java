package com.example.dpp.services;

import com.example.dpp.model.api.products.AddProduct;
import com.example.dpp.model.api.products.ProductInfo;

import java.util.List;

public interface IProductService {

    List<ProductInfo> getProducts();
    ProductInfo getProduct(int id);
    ProductInfo createProduct(AddProduct product);
    ProductInfo updateProduct(ProductInfo product);
    ProductInfo updateProduct(int id, AddProduct product);
    boolean deleteProduct(int id);
}
