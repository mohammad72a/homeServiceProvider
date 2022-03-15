package ir.maktab.firstspringboot.api.offer;

import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOfferCreateParam {
    private long proficientId;
    private long orderId;
    private Double suggestedPrice;
    private String workDuration;
    private String startTime;

    public HomeServiceOffer convert2HomeServiceOffer(HomeServiceOrder homeServiceOrder, Proficient proficient) {
        return HomeServiceOffer.builder()
                .proficient(proficient)
                .suggestedPrice(this.suggestedPrice)
                .workDuration(this.workDuration)
                .startTime(convertStringDateToInstant(startTime))
                .homeServiceOrder(homeServiceOrder)
                .build();
    }

    private Instant convertStringDateToInstant(String stringDate) {
        return LocalDateTime.parse(
                        stringDate,
                        DateTimeFormatter.ofPattern("hh:mm a, EEE M/d/uuuu", Locale.getDefault())
                )
                .atZone(TimeZone.getDefault().toZoneId())
                .toInstant();
    }
}
