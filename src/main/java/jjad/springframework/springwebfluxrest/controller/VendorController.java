package jjad.springframework.springwebfluxrest.controller;

import jjad.springframework.springwebfluxrest.domain.Category;
import jjad.springframework.springwebfluxrest.domain.Vendor;
import jjad.springframework.springwebfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.URL_API)
public class VendorController {

    public static final String URL_API = "/api/v1/vendors";

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> updateCategory(@PathVariable String id, Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> patchCategory(@PathVariable String id, Vendor vendor){
        return vendorRepository.findById(id)
                .flatMap(foundVendor -> {
                    if(vendor.getName() != foundVendor.getName()){
                        vendor.setName(foundVendor.getName());
                        return vendorRepository.save(vendor);
                    }
                    return Mono.just(foundVendor);
                });
    }

}
