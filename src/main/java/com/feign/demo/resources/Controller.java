package com.feign.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.feign.demo.interfaces.FeignInterface;
import com.feign.demo.model.Product;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/client")
public class Controller {

	@Autowired
	FeignInterface client;
	@Autowired RestTemplate template;
	@Autowired Environment environment;
	
	@CircuitBreaker(name = "client-cb", fallbackMethod = "generateResponse")
	@GetMapping("/break")
	public String breakIt() {
		String url = "https://boredcrefgregfrfgref.com/random";
		return template.getForEntity(url, String.class).toString();
	}
	@GetMapping("/products")
	public List<Product> getProducts() throws Exception{
		return client.getProducts();
		
	}
	
	@GetMapping("/product/{id}")
	public Product getProductByID(@PathVariable long id){
		return client.getProductById(id);
	}
	
	@GetMapping("/product-cat/{category}")
	public Product getProductByCategory(@PathVariable String category){
		return client.getProductByCategory(category);
	}
	
	public ResponseEntity<?> generateResponse(Throwable throwable){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("server is unavailable temporarily, please try after some time");
	}
	
	@GetMapping("/port")
	public String getPort() {
		return environment.getProperty("server.port");
	}
	
}
