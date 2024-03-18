package com.clustered.data.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import com.clustered.data.warehouse.controller.FxDealController;
import com.clustered.data.warehouse.exception.ExistFxDealException;
import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;
import com.clustered.data.warehouse.validator.FxDealValidator;

@ExtendWith(MockitoExtension.class)
public class FxDealControllerTest {
	@Mock
	private FxDealService fxDealService;

	@Mock
	private FxDealValidator fxDealValidator;

	@InjectMocks
	private FxDealController fxDealController;

	private FxDeal fxDeal;

	@BeforeEach
	public void setUp() {
		fxDeal = new FxDeal();
		fxDeal.setUniqueId("fx10");
		fxDeal.setFromCurrency("JD");
		fxDeal.setToCurrency("USD");
		fxDeal.setDealTimestamp(Timestamp.valueOf(LocalDateTime.now()));
		fxDeal.setDealAmount(BigDecimal.valueOf(3500));
	}

	@Test
	public void testGetFxDealByUniqueIdSuccess() {
		String uniqueId = "fx10";
		FxDeal fxDeal = new FxDeal();
		fxDeal.setUniqueId(uniqueId);

		when(fxDealService.findFxDealByUniqueId(uniqueId)).thenReturn(fxDeal);

		ResponseEntity<FxDealResponse> responseEntity = fxDealController.getFxDealByUniqueId(uniqueId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Fx-Deal retrieved successfully", responseEntity.getBody().getMessage());
		verify(fxDealService, times(1)).findFxDealByUniqueId(uniqueId);
	}

	@Test
	public void testGetFxDealByUniqueIdNotFound() {
		String uniqueId = "fx10";

		when(fxDealService.findFxDealByUniqueId(uniqueId)).thenReturn(null);

		ResponseEntity<FxDealResponse> responseEntity = fxDealController.getFxDealByUniqueId(uniqueId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("FX-Deal Not Found", responseEntity.getBody().getMessage());
		verify(fxDealService, times(1)).findFxDealByUniqueId(uniqueId);
	}

	@Test
    public void testCreateFxDealSuccess() {
        when(fxDealService.saveFxDeal(any(FxDeal.class))).thenReturn(new FxDealResponse("Fx-Deal created successfully", fxDeal));
        BindingResult result = new MapBindingResult(new HashMap<>(), "fxDeal");

        ResponseEntity<FxDealResponse> response = fxDealController.createFxDeal(fxDeal, result);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Fx-Deal created successfully", response.getBody().getMessage());
        verify(fxDealService, times(1)).saveFxDeal(any(FxDeal.class));
    }

	@Test
	public void testCreateFxDealValidationFailed() {
		BindingResult result = new MapBindingResult(new HashMap<>(), "fxDeal");
		result.rejectValue("uniqueId", "field.required", "uniqueId is required");

		ResponseEntity<FxDealResponse> response = fxDealController.createFxDeal(fxDeal, result);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(
				"Validation failed: [Field error in object 'fxDeal' on field 'uniqueId': rejected value [null]; codes [field.required.fxDeal.uniqueId,field.required.uniqueId,field.required]; arguments []; default message [uniqueId is required]]",
				response.getBody().getMessage());
	}

	@Test
	public void testCreateFxDealExistFxDealException() throws ExistFxDealException {
		doThrow(new ExistFxDealException("Fx-Deal already exists")).when(fxDealService).saveFxDeal(any(FxDeal.class));

		ResponseEntity<FxDealResponse> response = fxDealController.createFxDeal(fxDeal,
				new MapBindingResult(new HashMap<>(), "fxDeal"));

		assertNotNull(response);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("Fx-Deal with uniqueId : fx10, already exists.", response.getBody().getMessage());
	}

	@Test
	public void testDeleteFxDealByUniqueIdSuccess() {
		String uniqueId = "fx10";
		FxDeal fxDeal = new FxDeal();
		fxDeal.setUniqueId(uniqueId);

		when(fxDealService.findFxDealByUniqueId(uniqueId)).thenReturn(fxDeal);
		when(fxDealService.deleteFxDeal(fxDeal)).thenReturn(new FxDealResponse("Fx-Deal deleted successfully", fxDeal));

		ResponseEntity<FxDealResponse> responseEntity = fxDealController.deleteFxDealByUniqueId(uniqueId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Fx-Deal deleted successfully", responseEntity.getBody().getMessage());
		verify(fxDealService, times(1)).findFxDealByUniqueId(uniqueId);
		verify(fxDealService, times(1)).deleteFxDeal(fxDeal);
	}

	@Test
	public void testDeleteFxDealByUniqueIdNotFound() {
		String uniqueId = "fx10";

		when(fxDealService.findFxDealByUniqueId(uniqueId)).thenReturn(null);

		ResponseEntity<FxDealResponse> responseEntity = fxDealController.deleteFxDealByUniqueId(uniqueId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("FX-Deal Not Found", responseEntity.getBody().getMessage());
		verify(fxDealService, times(1)).findFxDealByUniqueId(uniqueId);
	}
}
