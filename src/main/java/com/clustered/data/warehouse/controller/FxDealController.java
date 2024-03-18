package com.clustered.data.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clustered.data.warehouse.exception.ExistFxDealException;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;
import com.clustered.data.warehouse.validator.FxDealValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FxDealController {
	private final FxDealService fxDealService;
	private final FxDealValidator fxDealValidator;

	/**
	 * Creates a new FxDeal record.
	 *
	 * @param fxDeal The FxDeal object containing the details of the FxDeal to be
	 *               created.
	 * @return A ResponseEntity containing the FxDealResponse object with the
	 *         created FxDeal's details.
	 *         
	 * Note : we can use @Valid before body to auto validation depend the annotations i set it in model FxDeal
	 */

	@PostMapping("/fxdeals")
	public ResponseEntity<FxDealResponse> createFxDeal(@RequestBody FxDeal fxDeal, BindingResult result) {
		log.info("Start createFxDeal({})", fxDeal.getUniqueId());
		try {
			fxDealValidator.validate(fxDeal, result);
			if (result.hasErrors()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new FxDealResponse("Validation failed: " + result.getAllErrors(), new FxDeal()));
			}
			FxDealResponse response = fxDealService.saveFxDeal(fxDeal);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (ExistFxDealException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new FxDealResponse(
					"Fx-Deal with uniqueId : " + fxDeal.getUniqueId() + ", already exists.", fxDeal));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new FxDealResponse("Error creating FxDeal", new FxDeal()));
		} finally {
			log.info("end createFxDeal({})", fxDeal.getUniqueId());
		}
	}

	/**
	 * Retrieves an FxDeal record by its uniqueId.
	 *
	 * @param uniqueId The unique identifier of the FxDeal to be retrieved.
	 * @return A ResponseEntity containing the FxDealResponse object with the
	 *         retrieved FxDeal's details.
	 */

	@GetMapping("/fxdeals/{uniqueId}")
	public ResponseEntity<FxDealResponse> getFxDealByUniqueId(@PathVariable String uniqueId) {
		log.info("Start getFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealService.findFxDealByUniqueId(uniqueId);
		if (fxDeal == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FxDealResponse("FX-Deal Not Found", new FxDeal()));
		}
		FxDealResponse response = new FxDealResponse("Fx-Deal retrieved successfully", fxDeal);
		log.info("end getFxDealByUniqueId({})", uniqueId);
		return ResponseEntity.ok(response);
	}

	/**
	 * Delete an FxDeal record by its uniqueId.
	 *
	 * @param uniqueId The unique identifier of the FxDeal to deleted.
	 * @return Delete successfully if the record exist else not found.
	 */

	@Transactional
	@DeleteMapping("/fxdeals/{uniqueId}")
	public ResponseEntity<FxDealResponse> deleteFxDealByUniqueId(@PathVariable String uniqueId) {
		log.info("Start deleteFxDealByUniqueId({})", uniqueId);
		FxDeal fxDeal = fxDealService.findFxDealByUniqueId(uniqueId);
		if (fxDeal == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FxDealResponse("FX-Deal Not Found", new FxDeal()));
		}
		FxDealResponse response = fxDealService.deleteFxDeal(fxDeal);
		log.info("end deleteFxDealByUniqueId({})", uniqueId);
		return ResponseEntity.ok(response);
	}
}
