package com.clustered.data.warehouse.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.clustered.data.warehouse.model.FxDeal;

import lombok.Data;

@Data
public class FxDealResponse {
	private String message;
	private Long id;
	private String uniqueId;
	private String fromCurrency;
	private String toCurrency;
	private BigDecimal dealAmount;
	private Timestamp dealTimestamp;

	public FxDealResponse(String message, FxDeal fxDeal) {
		this.message = message;
		this.id = fxDeal.getId();
		this.uniqueId = fxDeal.getUniqueId();
		this.fromCurrency = fxDeal.getFromCurrency();
		this.toCurrency = fxDeal.getToCurrency();
		this.dealAmount = fxDeal.getDealAmount();
		this.dealTimestamp = fxDeal.getDealTimestamp();
	}

}
