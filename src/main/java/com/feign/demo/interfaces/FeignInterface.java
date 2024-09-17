package com.feign.demo.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feign.demo.model.Product;

@FeignClient(value="feignClient", url="http://localhost:8071/product-service")
public interface FeignInterface {
	
	
	@RequestMapping(method=RequestMethod.GET,value="/products")
	public List<Product> getProducts();
	
	@GetMapping("product/{id}")
	public Product getProductById(@PathVariable long id);
	
	@GetMapping("product-cat/{category}")
	public Product getProductByCategory(@PathVariable String category);
	
	
}
