package com.sale.ticket.task.service.impl;

import com.sale.ticket.task.model.Billet;
import com.sale.ticket.task.model.Route;
import com.sale.ticket.task.repository.BilletRepository;
import com.sale.ticket.task.repository.RouteRepository;
import com.sale.ticket.task.service.BilletService;
import com.sale.ticket.task.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BilletServiceImpl implements BilletService {

    private final RouteRepository routeRepository;
    private final BilletRepository billetRepository;
    private final PaymentService paymentService;


    @Override
    public Integer createNewTicket(Billet billet, Integer id) {
        Billet newBillet = getNewBillet(billet, id);
        if (newBillet != null) {
            paymentService.createNewPayment(newBillet);
        }
        return newBillet.getId();
    }

    @Override
    public Billet getBilletByID(Integer id) {
        return billetRepository.findById(id).orElse(null);
    }

    @Transactional
    private Billet getNewBillet(Billet billet, Integer id) {
        Route route = routeRepository.getReferenceById(id);
        billet.setRoute(route);
        if (route.getCount() == 0) {
            throw new IllegalArgumentException("Свободных биллетов нет");
        }
        Billet newBillet = billetRepository.saveAndFlush(billet);
        route.setCount(route.getCount() - 1);
        return newBillet;
    }
}
