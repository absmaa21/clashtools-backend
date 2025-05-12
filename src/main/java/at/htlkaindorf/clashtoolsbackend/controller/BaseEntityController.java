package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.service.BaseEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-entities")
@RequiredArgsConstructor
public class BaseEntityController {

    private final BaseEntityService baseEntityService;

    @GetMapping
    public ResponseEntity<List<BaseEntityDTO>> getAllBaseEntities() {
        List<BaseEntityDTO> baseEntities = baseEntityService.getAllBaseEntities();
        return ResponseEntity.ok(baseEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseEntityDTO> getBaseEntityById(@PathVariable Long id) {
        BaseEntityDTO baseEntity = baseEntityService.getBaseEntityById(id);
        return ResponseEntity.ok(baseEntity);
    }

    @PostMapping
    public ResponseEntity<BaseEntityDTO> createBaseEntity(@Valid @RequestBody BaseEntityRequestDTO request) {
        BaseEntityDTO baseEntity = baseEntityService.createBaseEntity(request);
        return ResponseEntity.ok(baseEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseEntityDTO> updateBaseEntity(@PathVariable Long id, @Valid @RequestBody BaseEntityRequestDTO request) {
        BaseEntityDTO baseEntity = baseEntityService.updateBaseEntity(id, request);
        return ResponseEntity.ok(baseEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseEntity(@PathVariable Long id) {
        baseEntityService.deleteBaseEntity(id);
        return ResponseEntity.noContent().build();
    }
}
