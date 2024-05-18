package com.clustered.data.warehouse;

import com.clustered.data.warehouse.model.FxDeal;
import com.clustered.data.warehouse.repository.FxDealRepository;
import com.clustered.data.warehouse.response.FxDealResponse;
import com.clustered.data.warehouse.service.FxDealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxDealServiceTest {

    @Mock
    private FxDealRepository fxDealRepository;

    @InjectMocks
    private FxDealService fxDealService;

    @Test
    void testSaveFxDeal() {
        // Arrange
        String uniqueId = "testUniqueID";
        FxDeal fxDeal = new FxDeal();
        fxDeal.setUniqueId(uniqueId);
        fxDeal.setFromCurrency("USD");
        fxDeal.setToCurrency("EUR");
        fxDeal.setDealAmount(new BigDecimal("100.00"));
        fxDeal.setDealTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        when(fxDealRepository.existsByUniqueId(uniqueId)).thenReturn(false);
        when(fxDealRepository.save(any(FxDeal.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        FxDealResponse response = fxDealService.saveFxDeal(fxDeal);

        // Assert
        assertNotNull(response.getMessage());
        assertEquals("Fx-Deal created successfully", response.getMessage());
        assertFalse(fxDeal.isDealAmountNegative()); // Assuming isDealAmountPositive checks if the deal amount is greater than zero
        assertNotNull(fxDeal.getDealTimestamp());
        verify(fxDealRepository, times(1)).save(any(FxDeal.class));
    }
    @Test
    void testGetFxDealByUniqueId() {
        // Arrange
        String uniqueId = "testUniqueID";
        FxDeal fxDeal = new FxDeal();
        fxDeal.setUniqueId(uniqueId);
        fxDeal.setFromCurrency("USD");
        fxDeal.setToCurrency("EUR");
        fxDeal.setDealAmount(new BigDecimal("100.00"));
        when(fxDealRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(fxDeal));

        // Act
        FxDealResponse response = fxDealService.getFxDealByUniqueId(uniqueId);

        // Assert
        assertNotNull(response.getMessage());
        assertEquals("Fx-Deal retrieved successfully", response.getMessage());
        verify(fxDealRepository, times(1)).findByUniqueId(uniqueId);
    }
    @Test
    void testDeleteFxDealByUniqueId() {
        // Arrange
        String uniqueId = "testUniqueID";
        FxDeal fxDeal = new FxDeal();
        fxDeal.setUniqueId(uniqueId);
        fxDeal.setFromCurrency("USD");
        fxDeal.setToCurrency("EUR");
        fxDeal.setDealAmount(new BigDecimal("100.00"));
        when(fxDealRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(fxDeal));
        doReturn(1).when(fxDealRepository).deleteByUniqueId(uniqueId);

        // Act
        FxDealResponse response = fxDealService.deleteFxDealByUniqueId(uniqueId);

        // Assert
        assertNotNull(response.getMessage());
        assertEquals("Fx-Deal deleted successfully", response.getMessage());
        verify(fxDealRepository, times(1)).deleteByUniqueId(uniqueId);
    }

}
