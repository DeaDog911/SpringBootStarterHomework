package org.deadog.springbootstarterhomework.repository;

import org.deadog.springbootstarterhomework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
