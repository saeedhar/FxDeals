package com.clustered.data.warehouse.service;

import org.springframework.stereotype.Service;

import com.clustered.data.warehouse.exception.ExistFxDealException;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.repository.FxDealRepository;
import com.clustered.data.warehouse.response.FxDealResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FxDealService {
	private final FxDealRepository fxDealRepository;

	/**
	 * Saves a new FxDeal record.
	 *
	 * @param fxDeal The FxDeal object containing the details of the FxDeal to be
	 *               saved.
	 * @return The saved FxDeal object.
	 * @throws ExistFxDealException if a FxDeal with the same uniqueId already
	 *                                  exists.
	 */
	public FxDealResponse saveFxDeal(FxDeal fxDeal) {
		log.info("start saveFxDeal({})", fxDeal.getUniqueId());
		if (fxDealRepository.existsByUniqueId(fxDeal.getUniqueId())) {
			log.error("Fx-Deal with uniqueId: {} already exists.", fxDeal.getUniqueId());
			throw new ExistFxDealException("Fx-Deal with uniqueId :" + fxDeal.getUniqueId() + ", already exists.");
		}
		FxDeal savedDeal = fxDealRepository.save(fxDeal);
		log.info("end saveFxDeal({})", fxDeal.getUniqueId());
		return new FxDealResponse("Fx-Deal created successfully", savedDeal);
	}

	/**
	 * Retrieves an FxDeal record by its uniqueId.
	 *
	 * @param uniqueId The unique identifier of the FxDeal to be retrieved.
	 * @return The retrieved FxDeal object.
	 */

	public FxDeal findFxDealByUniqueId(String uniqueId) {
		log.info("start findFxDealByUniqueId({})", uniqueId);
		try {
			return fxDealRepository.findByUniqueId(uniqueId).orElse(null);
		} finally {
			log.info("end findFxDealByUniqueId({})", uniqueId);
		}
	}
}
