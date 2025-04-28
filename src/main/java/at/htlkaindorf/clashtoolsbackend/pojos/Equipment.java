package at.htlkaindorf.clashtoolsbackend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    private List<Attribute> activeAbilities;
    private List<Attribute> passiveAbilities;
    private List<Attribute> heroBoosts;
}
