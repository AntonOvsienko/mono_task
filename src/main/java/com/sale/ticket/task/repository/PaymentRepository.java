package com.sale.ticket.task.repository;

import com.sale.ticket.task.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query (value = "SELECT p FROM Payment p"
            + " JOIN p.billet pb"
            + " JOIN p.status ps"
            + " JOIN pb.route pbr"
            + " JOIN pbr.city pbrc"
            + " WHERE pb.id = id")
    Payment getPaymentByBilletId(@Param ("id") Integer id);
}
