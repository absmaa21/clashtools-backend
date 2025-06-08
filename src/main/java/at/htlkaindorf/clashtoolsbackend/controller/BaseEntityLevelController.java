package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import at.htlkaindorf.clashtoolsbackend.service.BaseEntityLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/base-entity-levels")
@Tag(name = "Base Entity Levels", description = "API for managing base entity levels")
public class BaseEntityLevelController extends CrudController<BaseEntityLevel, BaseEntityLevelResponseDTO, BaseEntityLevelRequestDTO, Long> {

    private final BaseEntityLevelService baseEntityLevelService;

    public BaseEntityLevelController(BaseEntityLevelService baseEntityLevelService) {
        super(baseEntityLevelService);
        this.baseEntityLevelService = baseEntityLevelService;
    }


    @GetMapping("/base-entity/{baseEntityId}")
    @Operation(summary = "Get base entity levels by base entity ID",
               description = "Retrieves all base entity levels associated with a specific base entity")
    public ResponseEntity<ApiResponse<List<BaseEntityLevelResponseDTO>>> getBaseEntityLevelsByBaseEntityId(
            @PathVariable Long baseEntityId) {
        List<BaseEntityLevelResponseDTO> baseEntityLevels = baseEntityLevelService.getBaseEntityLevelsByBaseEntityId(baseEntityId);
        return ResponseEntity.ok(ApiResponse.success(baseEntityLevels));
    }




    @DeleteMapping("/base-entity/{baseEntityId}")
    @Operation(summary = "Delete base entity levels by base entity ID",
               description = "Deletes all base entity levels associated with a specific base entity")
    public ResponseEntity<ApiResponse<Void>> deleteBaseEntityLevelsByBaseEntityId(
            @PathVariable Long baseEntityId) {
        baseEntityLevelService.deleteBaseEntityLevelsByBaseEntityId(baseEntityId);
        return ResponseEntity.ok(ApiResponse.success(null, "Base entity levels deleted successfully"));
    }
}
