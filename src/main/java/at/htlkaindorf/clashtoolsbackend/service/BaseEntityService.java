package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseEntityService {

    private final BaseEntityRepository baseEntityRepository;
    private final BaseEntityMapper baseEntityMapper;

    public List<BaseEntityDTO> getAllBaseEntities() {
        List<BaseEntity> baseEntities = baseEntityRepository.findAll();
        return baseEntityMapper.toDTOList(baseEntities);
    }

    public BaseEntityDTO getBaseEntityById(Long id) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));
        return baseEntityMapper.toDTO(baseEntity);
    }

    public BaseEntityDTO createBaseEntity(BaseEntityRequestDTO request) {
        BaseEntity baseEntity = baseEntityMapper.toEntity(request);
        BaseEntity savedBaseEntity = baseEntityRepository.save(baseEntity);
        return baseEntityMapper.toDTO(savedBaseEntity);
    }

    public BaseEntityDTO updateBaseEntity(Long id, BaseEntityRequestDTO request) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));

        baseEntity.getBaseEntityName().setName(request.getName());
        BaseEntity updatedBaseEntity = baseEntityRepository.save(baseEntity);
        return baseEntityMapper.toDTO(updatedBaseEntity);
    }

    public void deleteBaseEntity(Long id) {
        baseEntityRepository.deleteById(id);
    }
}
