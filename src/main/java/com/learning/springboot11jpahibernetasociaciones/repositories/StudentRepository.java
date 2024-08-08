package com.learning.springboot11jpahibernetasociaciones.repositories;

import com.learning.springboot11jpahibernetasociaciones.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
