package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
