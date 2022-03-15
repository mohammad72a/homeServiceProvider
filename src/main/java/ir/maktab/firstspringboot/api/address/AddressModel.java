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
public class AddressModel {

    private Long id;
    private String province;
    private String city;
    private String street;
    private String alley;
    private String plaque;

    public AddressModel convertAddress2Model(Address address) {
        return AddressModel.builder()
                .id(address.getId())
                .province(address.getProvince())
                .city(address.getCity())
                .street(address.getStreet())
                .alley(address.getAlley())
                .plaque(address.getPlaque())
                .build();
    }
}
