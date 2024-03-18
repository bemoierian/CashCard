package com.example.cashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
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
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
//        The @PathVariable annotation is used to bind the URI template variable to the parameter of the method.
//        Spring Web will extract the value of the {requestedId} placeholder from the URI and pass it as an argument to findById.
//        The Principal object is used to get the name of the authenticated user.
//        Spring Web will inject the Principal object into the method for us.
//        We can use the Principal object to get the name of the authenticated user.
//        principal.getName() will return the username provided from Basic Auth

        // Find a cash card in the database by its id
        // Optional: a container object which may or may not contain a non-null value
        Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));        if (cashCardOptional.isPresent()) {
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
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        // Create a new cash card in the database
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
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
    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        // Pageable is another object that Spring Web provides for us.
        // Since we specified the URI parameters of page=0&size=1, pageable will contain the values we need
        Page<CashCard> page = cashCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        if (cashCard != null) {
            CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}