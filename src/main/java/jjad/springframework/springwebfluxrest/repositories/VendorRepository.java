package jjad.springframework.springwebfluxrest.repositories;

import jjad.springframework.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
