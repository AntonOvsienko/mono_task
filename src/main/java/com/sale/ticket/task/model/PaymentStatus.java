package com.sale.ticket.task.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_status")
@EqualsAndHashCode(of = "id")
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    public PaymentStatus(String status) {
        this.status = status;
    }
}
