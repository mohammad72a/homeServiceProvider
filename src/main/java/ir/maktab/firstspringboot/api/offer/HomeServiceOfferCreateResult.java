package ir.maktab.firstspringboot.api.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOfferCreateResult {
    private long homeServiceOfferId;
}
