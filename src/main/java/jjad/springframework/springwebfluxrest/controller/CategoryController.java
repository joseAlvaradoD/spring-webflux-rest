package jjad.springframework.springwebfluxrest.controller;

import jjad.springframework.springwebfluxrest.domain.Category;
import jjad.springframework.springwebfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.URL_API)
public class CategoryController {

    public static final String URL_API = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> getCategoryById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> updateCategory(@PathVariable String id, Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> patchCategory(@PathVariable String id, Category category){
        return categoryRepository.findById(id)
                .flatMap(foundCategory -> {
                    if(category.getDescription() != foundCategory.getDescription()){
                        category.setDescription(foundCategory.getDescription());
                        return categoryRepository.save(category);
                    }
                   return Mono.just(foundCategory);
                });
    }

}
