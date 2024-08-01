package com.learning.springboot11jpahibernetasociaciones.repositories;

import com.learning.springboot11jpahibernetasociaciones.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
