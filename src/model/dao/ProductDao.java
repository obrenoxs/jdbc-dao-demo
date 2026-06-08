package model.dao;

import java.util.List;

import model.entities.Product;

public interface ProductDao {

	Product findById(Integer Id);
	List<Product> findAll();
	void insert(Product obj);
	void update(Product obj);
	void deleteById(Integer id);
}
