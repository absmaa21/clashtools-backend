package at.htlkaindorf.clashtoolsbackend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteTargetPet {
    private FavoriteTarget favoriteTargetEnum;
    private int multiplicator;
    private Range radius;
}
