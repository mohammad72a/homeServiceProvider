package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.model.entity.Address;
import ir.maktab.firstspringboot.model.repository.AddressRepository;
import org.springframework.stereotype.Service;


@Service
public class AddressService extends BaseService<Address, Long> {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        setJpaRepository(addressRepository);
        this.addressRepository = addressRepository;
    }

}
