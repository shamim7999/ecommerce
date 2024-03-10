package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    public ConfirmationToken findByConfirmationToken(String confirmationToken);
}
