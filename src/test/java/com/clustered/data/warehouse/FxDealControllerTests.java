package com.clustered.data.warehouse;

import com.clustered.data.warehouse.controller.FxDealController;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FxDealController.class)
public class FxDealControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FxDealService fxDealService;

	@Test
	void testCreateFxDeal() throws Exception {
		FxDeal fxDeal = new FxDeal();
		fxDeal.setUniqueId("testUniqueID");
		fxDeal.setFromCurrency("USD");
		fxDeal.setToCurrency("EUR");
		fxDeal.setDealAmount(new BigDecimal("100.00"));

		when(fxDealService.saveFxDeal(fxDeal)).thenReturn(new FxDealResponse("Fx-Deal created successfully", fxDeal));

		mockMvc.perform(post("/api/fxdeals")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fxDeal)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Fx-Deal created successfully"))
				.andExpect(jsonPath("$.data.uniqueId").value("testUniqueID"))
				.andExpect(jsonPath("$.data.fromCurrency").value("USD"))
				.andExpect(jsonPath("$.data.toCurrency").value("EUR"))
				.andExpect(jsonPath("$.data.dealAmount").value("100.00"));
	}

	@Test
	void testGetFxDealByUniqueId() throws Exception {
		String uniqueId = "testUniqueID";
		FxDeal fxDeal = new FxDeal();
		fxDeal.setUniqueId(uniqueId);
		fxDeal.setFromCurrency("USD");
		fxDeal.setToCurrency("EUR");
		fxDeal.setDealAmount(new BigDecimal("100.00"));

		when(fxDealService.getFxDealByUniqueId(uniqueId)).thenReturn(new FxDealResponse("Fx-Deal retrieved successfully", fxDeal));

		mockMvc.perform(get("/api/fxdeals/" + uniqueId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Fx-Deal retrieved successfully"))
				.andExpect(jsonPath("$.data.uniqueId").value(uniqueId))
				.andExpect(jsonPath("$.data.fromCurrency").value("USD"))
				.andExpect(jsonPath("$.data.toCurrency").value("EUR"))
				.andExpect(jsonPath("$.data.dealAmount").value("100.00"));
	}

	@Test
	void testDeleteFxDealByUniqueId() throws Exception {
		String uniqueId = "testUniqueID";
		when(fxDealService.deleteFxDealByUniqueId(uniqueId)).thenReturn(new FxDealResponse("Fx-Deal deleted successfully", new FxDeal()));

		mockMvc.perform(delete("/api/fxdeals/" + uniqueId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Fx-Deal deleted successfully"));
	}
}
