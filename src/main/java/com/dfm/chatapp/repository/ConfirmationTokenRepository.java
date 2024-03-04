package com.dfm.chatapp.repository;

import com.dfm.chatapp.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    List<ConfirmationToken> findByIsExpired(boolean searchTrueOrFalse);
}
