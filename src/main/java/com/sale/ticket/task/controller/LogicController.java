package com.sale.ticket.task.controller;

import com.sale.ticket.task.model.*;
import com.sale.ticket.task.service.BilletService;
import com.sale.ticket.task.service.PaymentService;
import com.sale.ticket.task.service.RouteService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class LogicController {

    private final BilletService billetService;
    private final PaymentService paymentService;
    private final RouteService routeService;

    @RequestMapping ("/")
    public String showFirstPage() {
        return "index.html";
    }

    @PostMapping ("/route-list")
    public String showAllRoute(Model model) {
        List<Route> routeList = routeService.getAllRoute();
        model.addAttribute("routeList", routeList);
        model.addAttribute("billetPresent", false);

        return "route-list.html";
    }

    @GetMapping ("/ticket-info")
    public String ticketInformation(Model model) {
        model.addAttribute("paymentPresent", false);

        return "ticket-info.html";
    }

    @PostMapping ("/service-payment")
    public String paymentInformation(Model model) {
        model.addAttribute("paymentMessage", "");

        return "service-payment.html";
    }

    @PostMapping ("/service-payment/buy")
    public String addPay(Model model, @RequestParam ("id") @NonNull Integer id,
                         @RequestParam ("name") @NonNull String name,
                         @RequestParam ("surname") @NonNull String surname,
                         @RequestParam ("patronomic") @NonNull String patronimic) {
        if (paymentService.getPaymentByIdBilletAndInitial(id, name, surname, patronimic)){
            model.addAttribute("paymentMessage", "???????????? ???????????? ????????????");
        } else {
            model.addAttribute("paymentMessage", "?????????????????? ?????????? ???? ???????????????????? ?????????????????? ???? ????????????");
        }

        return "service-payment.html";
    }

    @GetMapping ("/ticket-info/get")
    public String getTicketInformation(@RequestParam ("id") @NonNull Integer id, Model model) {
        Payment payment = paymentService.getPaymentByIdBillet(id);
        if (payment == null) {
            model.addAttribute("paymentPresent", false);
            model.addAttribute("exceptionMessage", "?????????? ?? ?????????? ?????????????? ???? ????????????");
        } else {
            model.addAttribute("paymentPresent", true);
            model.addAttribute("payment", payment);
        }
        return "ticket-info.html";
    }

    @PostMapping ("/buy-ticket")
    public String addNewTicket(@RequestParam ("id") @NonNull Integer id, @RequestParam ("firstname") @NonNull String firstname, @RequestParam ("surname") @NonNull String surname, @RequestParam ("patronomic") @NonNull String patronomic, Model model) {
        if (id != 0) {
            Billet billet = new Billet();
            billet.setFirstName(firstname);
            billet.setSurname(surname);
            billet.setPatronomic(patronomic);
            Integer billetId = billetService.createNewTicket(billet, id);
            model.addAttribute("billetIndex", billetId);
            model.addAttribute("billetPresent", true);
        }
        List<Route> routeList = routeService.getAllRoute();
        model.addAttribute("routeList", routeList);

        return "route-list.html";
    }
}
