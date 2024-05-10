package com.example.nvclothes.service;

import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.exception.AccessoryNotFoundException;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccessoriesEntityServiceTest {

    @InjectMocks
    private IAccessoriesEntityService accessoriesEntityService;

    @Mock
    private AccessoriesEntityRepositoryInterface accessoriesEntityRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccessoryEntity() {
        AccessoriesDto accessoriesDto = AccessoriesDto.builder()
                .brand(Brand.NIKE.getDisplayName())
                .name("Black hoodie")
                .cost(159L)
                .amount(100L)
                .accessoriesId(1L)
                .id(1L)
                .build();
        when(accessoriesEntityRepository.save(any(AccessoriesEntity.class))).thenReturn(null);
        //doNothing().when(accessoriesEntityRepository).save(any(AccessoriesEntity.class));
        try{
            accessoriesEntityService.createAccessoryEntity(accessoriesDto);
            assertEquals(true,true);
        } catch (Exception e){
            assertEquals(true, false);
        }
    }

    @Test
    public void testGetAccessoryEntityById() throws AccessoryNotFoundException {
        Long id = 1L;
        List<AccessoriesEntity> accessoriesEntities = new ArrayList<>();
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(1L)
                .productId(1L)
                .attribute(Attribute.BRAND.getDisplayName())
                .value(Brand.NIKE.getDisplayName())
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(2L)
                .productId(1L)
                .attribute(Attribute.NAME.getDisplayName())
                .value("Block logo t-shirt")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(3L)
                .productId(1L)
                .attribute(Attribute.COST.getDisplayName())
                .value("159")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(4L)
                .productId(1L)
                .attribute(Attribute.AMOUNT.getDisplayName())
                .value("100")
                .build());
        when(accessoriesEntityRepository.getAccessoriesEntitiesByProductId(id)).thenReturn(accessoriesEntities);

        AccessoriesEntity result = accessoriesEntityService.getAccessoryEntityById(id);

        assertEquals(Brand.NIKE, result.getBrand());
        assertEquals("Block logo t-shirt", result.getName());
        assertEquals(159L, result.getCost());
        assertEquals(100L, result.getAmount());
    }

    @Test
    public void testGetAccessoryEntityById_withInvalidId() {
        Long id = 1L;
        when(accessoriesEntityRepository.getAccessoriesEntitiesByProductId(id)).thenReturn(Collections.emptyList());

        try {
            accessoriesEntityService.getAccessoryEntityById(id);
            fail("Should have thrown AccessoryNotFoundException");
        } catch (AccessoryNotFoundException e) {
            assertEquals("Accessory not found for id: 1", e.getMessage());
        }
    }

    @Test
    public void testGetAllAccessoriesEntities() {
        List<AccessoriesEntity> accessoriesEntities = new ArrayList<>();
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(1L)
                .productId(1L)
                .attribute(Attribute.BRAND.getDisplayName())
                .value(Brand.NIKE.getDisplayName())
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(2L)
                .productId(1L)
                .attribute(Attribute.NAME.getDisplayName())
                .value("Block logo t-shirt")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(3L)
                .productId(1L)
                .attribute(Attribute.COST.getDisplayName())
                .value("159")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(4L)
                .productId(1L)
                .attribute(Attribute.AMOUNT.getDisplayName())
                .value("100")
                .build());

        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(5L)
                .productId(2L)
                .attribute(Attribute.BRAND.getDisplayName())
                .value(Brand.NIKE.getDisplayName())
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(6L)
                .productId(2L)
                .attribute(Attribute.NAME.getDisplayName())
                .value("Casual t-shirt")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(7L)
                .productId(2L)
                .attribute(Attribute.COST.getDisplayName())
                .value("40")
                .build());
        accessoriesEntities.add(AccessoriesEntity.builder()
                .id(8L)
                .productId(2L)
                .attribute(Attribute.AMOUNT.getDisplayName())
                .value("10")
                .build());

        when(accessoriesEntityRepository.findAll()).thenReturn(accessoriesEntities);

        List<AccessoriesEntity> result = accessoriesEntityService.getAllAccessoriesEntities();

        assertNotNull(result);
        assertEquals(2, result.size());

    }
}