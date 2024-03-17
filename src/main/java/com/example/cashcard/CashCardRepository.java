package com.example.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//This is a Spring Data repository. It's a special interface that Spring Data will implement for us.
//We don't need to write any code for it. We just need to define the interface and Spring Data will
//provide the implementation for us. This is a powerful feature of Spring Data that we'll use throughout this course.

//CashCard: This is the entity that we want to manage. Spring Data will provide us with methods to save, update, delete, and find CashCard entities.
//Long: This is the type of the primary key of the CashCard entity. In this case, it's a Long.
interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {
}
