package com.learning.springboot11jpahibernetasociaciones.repositories;

import com.learning.springboot11jpahibernetasociaciones.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository <Course, Long> {
}
