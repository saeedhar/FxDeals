package com.clustered.data.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clustered.data.warehouse.model.FxDeal;

public interface FxDealRepository extends JpaRepository<FxDeal, Long> {

	Optional<FxDeal> findByUniqueId(String uniqueId);

	boolean existsByUniqueId(String uniqueId);
}
