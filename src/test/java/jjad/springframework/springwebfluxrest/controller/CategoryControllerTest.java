package jjad.springframework.springwebfluxrest.controller;

import jjad.springframework.springwebfluxrest.domain.Category;
import jjad.springframework.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri(CategoryController.URL_API)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById(){
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get()
                .uri(CategoryController.URL_API + "/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(Mockito.any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("Cat").build()));

        Category categoryToSave = Category.builder().description("some desc").build();

        webTestClient.post()
                .uri(CategoryController.URL_API)
                .bodyValue(categoryToSave)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        BDDMockito.given(categoryRepository.save(Mockito.any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        Category categoryToUpdate = Category.builder().description("some desc").build();

        webTestClient.put()
                .uri(CategoryController.URL_API + "/someId")
                .bodyValue(categoryToUpdate)
                .exchange()
                .expectStatus()
                .isOk();
    }
}