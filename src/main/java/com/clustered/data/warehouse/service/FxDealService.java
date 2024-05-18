package com.clustered.data.warehouse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clustered.data.warehouse.exception.ExistFxDealException;
import com.clustered.data.warehouse.exception.NotFoundException;
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

	@Transactional
	public FxDealResponse saveFxDeal(FxDeal fxDeal) {
		log.info("Start saveFxDeal({})", fxDeal.getUniqueId());
		if (fxDealRepository.existsByUniqueId(fxDeal.getUniqueId())) {
			log.error("Fx-Deal with uniqueId: {} already exists.", fxDeal.getUniqueId());
			throw new ExistFxDealException(fxDeal.getUniqueId());
		}
		FxDeal savedDeal = fxDealRepository.save(fxDeal);
		log.info("End saveFxDeal({})", fxDeal.getUniqueId());
		return new FxDealResponse("Fx-Deal created successfully", savedDeal);
	}

	public FxDealResponse getFxDealByUniqueId(String uniqueId) {
		log.info("Start getFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealRepository.findByUniqueId(uniqueId).orElseThrow(() -> {
			log.error("Fx-Deal with uniqueId: {} not found.", uniqueId);
			return new NotFoundException(uniqueId);
		});
		log.info("End getFxDealByUniqueId({})", uniqueId);
		return new FxDealResponse("Fx-Deal retrieved successfully", fxDeal);
	}

	@Transactional
	public FxDealResponse deleteFxDealByUniqueId(String uniqueId) {
		log.info("Start deleteFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealRepository.findByUniqueId(uniqueId).orElseThrow(() -> {
			log.error("Fx-Deal with uniqueId: {} not found.", uniqueId);
			return new NotFoundException(uniqueId);
		});
		int isDeleted = fxDealRepository.deleteByUniqueId(fxDeal.getUniqueId());
		String deleteMessage = isDeleted > 0 ? "Fx-Deal deleted successfully" : "Failed to delete Fx-Deal";
		log.info("End deleteFxDealByUniqueId({})", uniqueId);
		return new FxDealResponse(deleteMessage, fxDeal);
	}
}
