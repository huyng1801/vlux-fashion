package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
}