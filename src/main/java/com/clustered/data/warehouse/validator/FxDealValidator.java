package com.clustered.data.warehouse.validator;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.clustered.data.warehouse.model.FxDeal;

@Component
public class FxDealValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FxDeal.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FxDeal entity = (FxDeal) target;

		if (entity.getUniqueId() == null || entity.getUniqueId().isEmpty()) {
			errors.rejectValue("uniqueId", "field.required", "uniquId is required");
		}
		if (entity.getUniqueId().length() < 3 || entity.getUniqueId().length() > 40) {
			errors.rejectValue("uniqueId", "field.invalid", "uniquId length must be > 3 and < 40 char");
		}
		if (entity.getFromCurrency() == null || entity.getFromCurrency().isEmpty()) {
			errors.rejectValue("fromCurrency", "field.required", "fromCurrency is required");
		}
		if (entity.getToCurrency() == null || entity.getToCurrency().isEmpty()) {
			errors.rejectValue("toCurrency", "field.required", "toCurrency is required");
		}
		if (entity.getDealTimestamp() == null) {
			errors.rejectValue("dealTimestamp", "field.required", "dealTimestamp is required");
		}
		if (entity.getDealTimestamp() != null && entity.getDealTimestamp().after(Timestamp.from(Instant.now()))) {
			errors.rejectValue("dealTimestamp", "future.timestamp", "Deal timestamp must be in the past.");
		}
		if (entity.getDealAmount() != null && entity.isDealAmountNegative()) {
			errors.rejectValue("dealAmount", "field.invalid", "dealAmount must be non-negative");
		}

	}
}
