package com.clustered.data.warehouse.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "fx_deal")
public class FxDeal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 40)
	private String uniqueId;

	@NotNull
	@Size(min = 2, max = 15)
	private String fromCurrency;

	@NotNull
	@Size(min = 2, max = 15)
	private String toCurrency;

	@NotNull
	@DecimalMin(value = "0.00")
	private BigDecimal dealAmount;

	@NotNull
	@Past
	private Timestamp dealTimestamp;

	public boolean isDealAmountNegative() {
		return dealAmount.compareTo(BigDecimal.ZERO) < 0;
	}
}
