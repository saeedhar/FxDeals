package com.clustered.data.warehouse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.clustered.data.warehouse.exception.NotFoundException;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;

@SpringBootTest
@AutoConfigureMockMvc
public class FxDealControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FxDealService fxDealService;

	@Test
	public void testCreateFxDeal() throws Exception {
		FxDealResponse fxDealResponse = new FxDealResponse("Fx-Deal created successfully", new FxDeal());

		when(fxDealService.saveFxDeal(any(FxDeal.class))).thenReturn(fxDealResponse);
		String fxDealJson = "{ \"uniqueId\": \"testId\", \"fromCurrency\": \"USD\", \"toCurrency\": \"EUR\", \"dealTimestamp\": \"2024-03-18T10:00:00Z\", \"dealAmount\": 1000 }";

		mockMvc.perform(post("/api/fxdeals").contentType("application/json").content(fxDealJson))
				.andExpect(status().isCreated());

		verify(fxDealService, times(1)).saveFxDeal(any(FxDeal.class));
	}

	@Test
	public void testCreateFxDealFaield() throws Exception {
		String fxDealJson = "{ \"uniqueId\": \"ab\", \"fromCurrency\": \"USD\", \"toCurrency\": \"EUR\", \"dealTimestamp\": \"2024-03-18T10:00:00Z\", \"dealAmount\": 1000 }";

		mockMvc.perform(post("/api/fxdeals").contentType("application/json").content(fxDealJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testGetFxDealByUniqueIdSuccess() throws Exception {
		// Given
		String uniqueId = "testId";
		FxDeal fxDeal = new FxDeal(); // Populate with necessary data
		FxDealResponse fxDealResponse = new FxDealResponse("Fx-Deal retrieved successfully", fxDeal);
		when(fxDealService.getFxDealByUniqueId(uniqueId)).thenReturn(fxDealResponse);
		mockMvc.perform(get("/api/fxdeals/{uniqueId}", uniqueId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Fx-Deal retrieved successfully"));
	}

	@Test
	public void testGetFxDealByUniqueIdNotFound() throws Exception {
		String uniqueId = "nonExistentId";
		when(fxDealService.getFxDealByUniqueId(uniqueId)).thenThrow(new NotFoundException(uniqueId));
		mockMvc.perform(get("/api/fxdeals/{uniqueId}", uniqueId)).andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteFxDealByUniqueIdSuccess() throws Exception {
		String uniqueId = "testId";
		FxDeal fxDeal = new FxDeal();
		FxDealResponse fxDealResponse = new FxDealResponse("Fx-Deal deleted successfully", fxDeal);
		when(fxDealService.deleteFxDealByUniqueId(uniqueId)).thenReturn(fxDealResponse);
		mockMvc.perform(delete("/api/fxdeals/{uniqueId}", uniqueId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Fx-Deal deleted successfully"));
		verify(fxDealService, times(1)).deleteFxDealByUniqueId(uniqueId);
	}

	@Test
	public void testDeleteFxDealByUniqueIdNotFound() throws Exception {
		String uniqueId = "nonExistentId";
		when(fxDealService.deleteFxDealByUniqueId(uniqueId)).thenThrow(new NotFoundException(uniqueId));
		mockMvc.perform(delete("/api/fxdeals/{uniqueId}", uniqueId)).andExpect(status().isNotFound());
	}
}
