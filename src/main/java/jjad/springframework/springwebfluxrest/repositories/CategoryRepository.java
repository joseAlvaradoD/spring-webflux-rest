package jjad.springframework.springwebfluxrest.repositories;

import jjad.springframework.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
