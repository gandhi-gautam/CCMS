package com.ccms.controller;

import com.ccms.model.Card;
import com.ccms.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    private final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addNewCardToUser(@PathVariable long userId, @RequestBody Card card) {
        return new ResponseEntity<>(cardService.addNewCardToUser(userId, card), HttpStatus.CREATED);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCardInfo(@PathVariable long cardId) {
        return ResponseEntity.ok(cardService.getCardInfo(cardId));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCardInfo(@PathVariable long cardId, @RequestBody Card card) {
        return ResponseEntity.ok(cardService.updateCardInfo(cardId, card));
    }

    @PutMapping("/activate/{cardId}")
    public ResponseEntity<?> activateCard(@PathVariable long cardId) {
        return ResponseEntity.ok(cardService.activateCard(cardId));
    }

    @PutMapping("/deactivate/{cardId}")
    public ResponseEntity<?> deActivateCard(@PathVariable long cardId) {
        return ResponseEntity.ok(cardService.deActivateCard(cardId));
    }

    @PutMapping("/adjustLimit/{cardId}")
    public ResponseEntity<?> adjustLimitOfTheCard(@PathVariable long cardId, @RequestParam int limit) {
        return ResponseEntity.ok(cardService.adjustLimitOfTheCard(cardId, limit));
    }
}
