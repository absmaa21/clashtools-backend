package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Range {
    private Double min;
    private Double max;
}
