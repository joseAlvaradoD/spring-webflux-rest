package jjad.springframework.springwebfluxrest.controller;

import jjad.springframework.springwebfluxrest.domain.Category;
import jjad.springframework.springwebfluxrest.domain.Vendor;
import jjad.springframework.springwebfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class VendorControllerTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().name("Vendor1").build(),
                        (Vendor.builder().name("Vendor2").build())));

        webTestClient.get()
                .uri(VendorController.URL_API)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().name("Vendor1").build()));

        webTestClient.get()
                .uri(VendorController.URL_API + "/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void createVendor() {
        BDDMockito.given(vendorRepository.saveAll(Mockito.any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().name("Name").build()));

        Vendor vendorToSave = Vendor.builder().name("some name").build();

        webTestClient.post()
                .uri(VendorController.URL_API)
                .bodyValue(vendorToSave)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        BDDMockito.given(vendorRepository.save(Mockito.any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().name("Name").build()));

        Vendor vendorToUpdate = Vendor.builder().name("some name").build();

        webTestClient.put()
                .uri(VendorController.URL_API + "/someId")
                .bodyValue(vendorToUpdate)
                .exchange()
                .expectStatus()
                .isOk();
    }
}