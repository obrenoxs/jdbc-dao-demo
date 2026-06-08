package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;

public class Program3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		ProductDao productDao = DaoFactory.createProductDao();
		
		System.out.println("===== TEST 1: product findById =====");
		Product product = productDao.findById(3);
		System.out.println(product);
		
		System.out.println("\n===== TEST 2: product findAll =====");
		List<Product> list = productDao.findAll();
		for (Product obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n===== TEST 3: product insert =====");
		Product newProduct = new Product(6, "Mousepad", 100.0);
		productDao.insert(newProduct);
		System.out.println("Inserted! New Product id = " + newProduct.getId());
		
		System.out.println("\n===== TEST 4: product update =====");
		product = productDao.findById(4);
		product.setName("New Headset");
		product.setPrice(400.0);
		productDao.update(product);
		System.out.println("Update completed");
		
		System.out.println("\n===== TEST 5: product delete =====");
		System.out.println("Enter id for delet test: ");
		int id = sc.nextInt();
		productDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}
}
