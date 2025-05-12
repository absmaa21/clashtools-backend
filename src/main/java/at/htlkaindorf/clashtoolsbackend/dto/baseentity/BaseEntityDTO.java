package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import lombok.Data;

@Data
public class BaseEntityDTO {
    private Long id;
    private String name;
    private int level;
}
