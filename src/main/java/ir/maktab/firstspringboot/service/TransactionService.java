package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.transaction.TransactionCreateParam;
import ir.maktab.firstspringboot.api.transaction.TransactionCreateResult;
import ir.maktab.firstspringboot.exception.CreditException;
import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.exception.TransactionException;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.Transaction;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.order.OrderStatus;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DecimalFormat;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerServiceImpl customerServiceImpl;

    public TransactionService(TransactionRepository transactionRepository, CustomerServiceImpl customerServiceImpl) {
        this.transactionRepository = transactionRepository;
        this.customerServiceImpl = customerServiceImpl;
    }

    @Transactional
    public TransactionCreateResult payOnline(TransactionCreateParam createParam) {
        Customer customer = customerServiceImpl.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getOrderId()));

        if (homeServiceOrder.getOrderStatus() != OrderStatus.FINISHED) {
            throw new TransactionException("Order not finished");
        }

        HomeServiceOffer homeServiceOffer = homeServiceOrder.getAcceptedOffer();

        return getTransactionCreateResult(createParam, customer, homeServiceOrder, homeServiceOffer);
    }

    @Transactional
    public TransactionCreateResult payWithCredit(TransactionCreateParam createParam) {
        createParam.setCustomerCreditCardNumber("Credit");

        Customer customer = customerServiceImpl.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getOrderId()));

        HomeServiceOffer homeServiceOffer = homeServiceOrder.getAcceptedOffer();

        if (homeServiceOrder.getOrderStatus() != OrderStatus.FINISHED) {
            throw new TransactionException("Order not finished");
        }

        if (customer.getCredit() < homeServiceOffer.getSuggestedPrice()) {
            throw new CreditException("Credit is not enough");
        }

        customer.setCredit(customer.getCredit() - homeServiceOffer.getSuggestedPrice());

        return getTransactionCreateResult(createParam, customer, homeServiceOrder, homeServiceOffer);
    }

    private TransactionCreateResult getTransactionCreateResult(
            TransactionCreateParam createParam,
            Customer customer,
            HomeServiceOrder homeServiceOrder,
            HomeServiceOffer homeServiceOffer) {

        Proficient proficient = homeServiceOffer.getProficient();

        DecimalFormat df = new DecimalFormat("0.00");
        proficient.setCredit(Double.parseDouble(
                df.format((proficient.getCredit() + (createParam.getAmount() * 0.7)))));

        homeServiceOrder.setOrderStatus(OrderStatus.PAID);

        Transaction transaction = createParam.convert2Transaction(customer, proficient, homeServiceOrder, homeServiceOffer);

        Transaction result = transactionRepository.save(transaction);

        return new TransactionCreateResult(result.getId());
    }
}

