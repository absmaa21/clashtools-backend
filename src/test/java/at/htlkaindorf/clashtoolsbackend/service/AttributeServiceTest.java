package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.*;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeTranslation;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeNameRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeRepository;
import at.htlkaindorf.clashtoolsbackend.service.AttributeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttributeServiceTest {

    @Mock
    private AttributeRepository attributeRepository;

    @Mock
    private AttributeNameRepository attributeNameRepository;

    @Mock
    private AttributeMapper attributeMapper;

    @InjectMocks
    private AttributeService attributeService;

    private Attribute testAttribute;
    private AttributeName testAttributeName;
    private AttributeResponseDTO testAttributeResponseDTO;
    private AttributeRequestDTO testAttributeRequestDTO;
    private List<Attribute> testAttributes;
    private List<AttributeResponseDTO> testAttributeResponseDTOs;

    @BeforeEach
    void setUp() {
        // Create test attribute name
        testAttributeName = new AttributeName();
        testAttributeName.setId(1L);
        testAttributeName.setName("Test Attribute Name");

        // Create test attribute
        testAttribute = new Attribute();
        testAttribute.setId(1L);
        testAttribute.setAttributeName(testAttributeName);
        testAttribute.setTranslations(new ArrayList<>());

        // Create test attribute translation
        AttributeTranslation translation = new AttributeTranslation();
        translation.setId(1L);
        translation.setLanguageCode("en");
        translation.setName("Test Translation");
        translation.setAttribute(testAttribute);
        testAttribute.getTranslations().add(translation);

        // Create test attribute name DTO
        AttributeNameDTO attributeNameDTO = new AttributeNameDTO();
        attributeNameDTO.setId(1L);
        attributeNameDTO.setName("Test Attribute Name");

        // Create test attribute translation DTO
        AttributeTranslationDTO translationDTO = new AttributeTranslationDTO();
        translationDTO.setId(1L);
        translationDTO.setLanguageCode("en");
        translationDTO.setName("Test Translation");

        // Create test attribute response DTO
        testAttributeResponseDTO = new AttributeResponseDTO();
        testAttributeResponseDTO.setId(1L);
        testAttributeResponseDTO.setAttributeName(attributeNameDTO);
        testAttributeResponseDTO.setTranslations(List.of(translationDTO));

        // Create test attribute request DTO
        testAttributeRequestDTO = new AttributeRequestDTO();
        testAttributeRequestDTO.setAttributeNameId(1L);
        testAttributeRequestDTO.setTranslations(List.of(translationDTO));

        // Create test attributes list
        testAttributes = List.of(testAttribute);

        // Create test attribute response DTOs list
        testAttributeResponseDTOs = List.of(testAttributeResponseDTO);
    }

    @Test
    void testGetAllAttributes() {
        // Arrange
        when(attributeRepository.findAll()).thenReturn(testAttributes);
        when(attributeMapper.toDTOList(testAttributes)).thenReturn(testAttributeResponseDTOs);

        // Act
        List<AttributeResponseDTO> result = attributeService.getAllAttributes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        // Verify
        verify(attributeRepository).findAll();
        verify(attributeMapper).toDTOList(testAttributes);
    }

    @Test
    void testGetAttributeById_WhenAttributeExists() {
        // Arrange
        when(attributeRepository.findById(1L)).thenReturn(Optional.of(testAttribute));
        when(attributeMapper.toDTO(testAttribute)).thenReturn(testAttributeResponseDTO);

        // Act
        AttributeResponseDTO result = attributeService.getAttributeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify
        verify(attributeRepository).findById(1L);
        verify(attributeMapper).toDTO(testAttribute);
    }

    @Test
    void testGetAttributeById_WhenAttributeDoesNotExist() {
        // Arrange
        when(attributeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> attributeService.getAttributeById(999L)
        );
        assertEquals("Attribute not found", exception.getMessage());

        // Verify
        verify(attributeRepository).findById(999L);
        verify(attributeMapper, never()).toDTO(any());
    }

    @Test
    void testCreateAttribute_Success() {
        // Arrange
        when(attributeNameRepository.findById(1L)).thenReturn(Optional.of(testAttributeName));
        when(attributeMapper.toEntity(testAttributeRequestDTO)).thenReturn(testAttribute);
        when(attributeRepository.save(testAttribute)).thenReturn(testAttribute);
        when(attributeMapper.toDTO(testAttribute)).thenReturn(testAttributeResponseDTO);

        // Act
        AttributeResponseDTO result = attributeService.createAttribute(testAttributeRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify
        verify(attributeNameRepository).findById(1L);
        verify(attributeMapper).toEntity(testAttributeRequestDTO);
        verify(attributeRepository).save(testAttribute);
        verify(attributeMapper).toDTO(testAttribute);
    }

    @Test
    void testCreateAttribute_AttributeNameNotFound() {
        // Arrange
        when(attributeNameRepository.findById(999L)).thenReturn(Optional.empty());
        testAttributeRequestDTO.setAttributeNameId(999L);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> attributeService.createAttribute(testAttributeRequestDTO)
        );
        assertEquals("AttributeName not found", exception.getMessage());

        // Verify
        verify(attributeNameRepository).findById(999L);
        verify(attributeMapper, never()).toEntity(any());
        verify(attributeRepository, never()).save(any());
    }

    @Test
    void testUpdateAttribute_Success() {
        // Arrange
        when(attributeRepository.findById(1L)).thenReturn(Optional.of(testAttribute));
        when(attributeNameRepository.findById(1L)).thenReturn(Optional.of(testAttributeName));
        when(attributeMapper.toEntity(testAttributeRequestDTO)).thenReturn(testAttribute);
        when(attributeRepository.save(testAttribute)).thenReturn(testAttribute);
        when(attributeMapper.toDTO(testAttribute)).thenReturn(testAttributeResponseDTO);

        // Act
        AttributeResponseDTO result = attributeService.updateAttribute(1L, testAttributeRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify
        verify(attributeRepository).findById(1L);
        verify(attributeNameRepository).findById(1L);
        verify(attributeRepository).save(testAttribute);
        verify(attributeMapper).toDTO(testAttribute);
    }

    @Test
    void testUpdateAttribute_AttributeNotFound() {
        // Arrange
        when(attributeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> attributeService.updateAttribute(999L, testAttributeRequestDTO)
        );
        assertEquals("Attribute not found", exception.getMessage());

        // Verify
        verify(attributeRepository).findById(999L);
        verify(attributeNameRepository, never()).findById(any());
        verify(attributeRepository, never()).save(any());
    }

    @Test
    void testUpdateAttribute_AttributeNameNotFound() {
        // Arrange
        when(attributeRepository.findById(1L)).thenReturn(Optional.of(testAttribute));
        when(attributeNameRepository.findById(999L)).thenReturn(Optional.empty());
        testAttributeRequestDTO.setAttributeNameId(999L);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> attributeService.updateAttribute(1L, testAttributeRequestDTO)
        );
        assertEquals("AttributeName not found", exception.getMessage());

        // Verify
        verify(attributeRepository).findById(1L);
        verify(attributeNameRepository).findById(999L);
        verify(attributeRepository, never()).save(any());
    }

    @Test
    void testDeleteAttribute() {
        // Act
        attributeService.deleteAttribute(1L);

        // Verify
        verify(attributeRepository).deleteById(1L);
    }

    @Test
    void testGetAttributesByAttributeNameId() {
        // Arrange
        when(attributeRepository.findByAttributeNameId(1L)).thenReturn(testAttributes);
        when(attributeMapper.toDTOList(testAttributes)).thenReturn(testAttributeResponseDTOs);

        // Act
        List<AttributeResponseDTO> result = attributeService.getAttributesByAttributeNameId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        // Verify
        verify(attributeRepository).findByAttributeNameId(1L);
        verify(attributeMapper).toDTOList(testAttributes);
    }
}
