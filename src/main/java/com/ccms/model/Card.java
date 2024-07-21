package com.ccms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "credit_limit ")
    private int creditLimit;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "amount_used")
    private int amountUsed;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Users user = new Users();
}
