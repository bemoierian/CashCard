package com.example.cashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {
    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        // Find a cash card in the database by its id
        // Optional: a container object which may or may not contain a non-null value
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        if (cashCardOptional.isPresent()) {
            // If the cash card is found, return it with a 200 OK status code
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            // If the cash card is not found, return a 404 Not Found status code
            return ResponseEntity.notFound().build();
        }
    }


//    The @RequestBody annotation is used to bind the HTTP request body to the parameter of the method.
//    Spring Web will deserialize the data into a CashCard for us and pass it as an argument to createCashCard.
//    The UriComponentsBuilder is a helper class to build URIs. It’s used to create a URI for the location of the new cash card.
//    UriComponentsBuilder is injected into the method by Spring Web (IoC).
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        // Create a new cash card in the database
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        // Create a URI for the location of the new cash card
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
                // {id} is a placeholder for the id of the new cash card
                // it will be replaced with the actual id of the new cash card: savedCashCard.id()
        // Return a 201 Created status code
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

}