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

		validateUniqueId(entity, errors);
		validateCurrency(entity.getFromCurrency(), "fromCurrency", errors);
		validateCurrency(entity.getToCurrency(), "toCurrency", errors);
		validateDealTimestamp(entity.getDealTimestamp(), errors);
		validateDealAmount(entity, errors);
	}

	private void validateUniqueId(FxDeal entity, Errors errors) {
		String uniqueId = entity.getUniqueId();
		if (uniqueId == null || uniqueId.isEmpty()) {
			errors.rejectValue("uniqueId", "field.required", "uniqueId is required");
		} else if (uniqueId.length() < 3 || uniqueId.length() > 40) {
			errors.rejectValue("uniqueId", "field.invalid", "uniqueId length must be between 3 and 40 characters");
		}
	}

	private void validateCurrency(String currency, String fieldName, Errors errors) {
		if (currency == null || currency.isEmpty()) {
			errors.rejectValue(fieldName, "field.required", fieldName + " is required");
		} else if (currency.length() < 2 || currency.length() > 4) {
			errors.rejectValue(fieldName, "field.invalid", fieldName + " length must be between 2 and 4 characters");
		}
	}

	private void validateDealTimestamp(Timestamp dealTimestamp, Errors errors) {
		if (dealTimestamp == null) {
			errors.rejectValue("dealTimestamp", "field.required", "dealTimestamp is required");
		} else if (dealTimestamp.after(Timestamp.from(Instant.now()))) {
			errors.rejectValue("dealTimestamp", "future.timestamp", "Deal timestamp must be in the past");
		}
	}

	private void validateDealAmount(FxDeal entity, Errors errors) {
		if (entity.getDealAmount() == null) {
			errors.rejectValue("dealAmount", "field.required", "dealAmount is required");
		} else if (entity.isDealAmountNegative()) {
			errors.rejectValue("dealAmount", "field.invalid", "dealAmount must be non-negative");
		}
	}
}
