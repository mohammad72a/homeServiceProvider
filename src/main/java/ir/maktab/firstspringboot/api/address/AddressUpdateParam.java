package ir.maktab.firstspringboot.api.address;

import ir.maktab.firstspringboot.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUpdateParam {

    private Long id;
    private String province;
    private String city;
    private String street;
    private String alley;
    private String plaque;

    public Address convert2Address() {
        return Address.builder()
                .id(this.id)
                .province(this.province)
                .city(this.city)
                .street(this.street)
                .alley(this.alley)
                .plaque(this.plaque)
                .build();
    }
}
