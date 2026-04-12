package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
