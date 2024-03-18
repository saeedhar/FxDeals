package com.clustered.data.warehouse.service;

import org.springframework.stereotype.Service;

import com.clustered.data.warehouse.exception.ExistFxDealException;
import com.clustered.data.warehouse.exception.NotFoundException;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.repository.FxDealRepository;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.validator.FxDealValidator;

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
	 *                              exists.
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

	public FxDealResponse getFxDealByUniqueId(String uniqueId) {
		log.info("start getFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealRepository.findByUniqueId(uniqueId).orElse(null);
		if (fxDeal == null) {
			throw new NotFoundException(uniqueId);
		}
		log.info("end getFxDealByUniqueId({})", uniqueId);
		return new FxDealResponse("Fx-Deal retrieved successfully", fxDeal);
	}

	/**
	 * Delete FxDeal record.
	 *
	 * @param uniqe id .
	 * @return "Fx-Deal deleted successfully".
	 */

	public FxDealResponse deleteFxDealByUniqueId(String uniqueId) {
		log.info("start deleteFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealRepository.findByUniqueId(uniqueId).orElse(null);
		if (fxDeal == null) {
			throw new NotFoundException(uniqueId);
		}
		int isDeleted = fxDealRepository.deleteByUniqueId(fxDeal.getUniqueId());
		String deleteMessage = isDeleted > 0 ? "Fx-Deal deleted successfully" : "Failed to delete Fx-Deal";
		log.info("end deleteFxDealByUniqueId({})", uniqueId);
		return new FxDealResponse(deleteMessage, fxDeal);
	}
}
