package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email=:email")
    public User getUserByUserName(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isEnabled = false WHERE u.id = :userId")
    public void softDelete(int userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isEnabled = true WHERE u.id = :userId")
    public void enableUser(int userId);

    public User findByEmailIgnoreCase(String email);
}
