package com.learning.springboot11jpahibernetasociaciones.repositories;

import com.learning.springboot11jpahibernetasociaciones.entities.ClientDetails;
import org.springframework.data.repository.CrudRepository;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long> {

}
