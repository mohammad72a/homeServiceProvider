package ir.maktab.firstspringboot.api.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOrderCreateResult {
    private long homeServiceOrderId;
}