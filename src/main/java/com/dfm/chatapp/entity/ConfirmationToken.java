package com.dfm.chatapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@Table(name = "confirmation_token")
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long tokenid;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "is_expired")
    private boolean isExpired = expirationDate != null && expirationDate.before(new Date());

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiration_in_minutes")
    private int expirationInMinutes = 1;

    public ConfirmationToken() {
    }

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
        expirationDate = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationInMinutes));
    }

    // Updates the field isExpired
    public boolean isExpired() {
        isExpired = expirationDate != null && expirationDate.before(new Date());
        return isExpired;
    }
}
