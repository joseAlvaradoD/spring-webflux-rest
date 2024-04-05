package jjad.springframework.springwebfluxrest.bootstrap;

import jjad.springframework.springwebfluxrest.domain.Category;
import jjad.springframework.springwebfluxrest.domain.Vendor;
import jjad.springframework.springwebfluxrest.repositories.CategoryRepository;
import jjad.springframework.springwebfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setDescription("Fruits");

        Category dried = new Category();
        dried.setDescription("Dried");

        Category fresh = new Category();
        fresh.setDescription("Fresh");

        Category exotic = new Category();
        exotic.setDescription("Exotic");

        Category nuts = new Category();
        nuts.setDescription("Nuts");

        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();

        System.out.println("Categories Loaded: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1).block();

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2).block();

        System.out.println("Vendors Loaded: " + vendorRepository.count().block());
    }

}
