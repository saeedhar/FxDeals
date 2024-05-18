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

import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FxDealController {
	private final FxDealService fxDealService;

	@PostMapping("/fxdeals")
	public ResponseEntity<?> createFxDeal(@Valid @RequestBody FxDeal fxDeal, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (var error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		FxDealResponse response = fxDealService.saveFxDeal(fxDeal);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/fxdeals/{uniqueId}")
	public ResponseEntity<FxDealResponse> getFxDealByUniqueId(@PathVariable String uniqueId) {
		FxDealResponse response = fxDealService.getFxDealByUniqueId(uniqueId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/fxdeals/{uniqueId}")
	public ResponseEntity<?> deleteFxDealByUniqueId(@PathVariable String uniqueId) {
		FxDealResponse response = fxDealService.deleteFxDealByUniqueId(uniqueId);
		return ResponseEntity.ok(response);
	}
}
