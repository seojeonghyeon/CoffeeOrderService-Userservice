package com.zayden.coffeeorderserviceuserservice.jpa;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserPointRepository extends CrudRepository<UserPointEntity, Long> {
    Optional<UserPointEntity> findByUserId(String userId);
}
