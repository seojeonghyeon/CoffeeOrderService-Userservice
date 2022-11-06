package com.zayden.coffeeorderserviceuserservice.dto;


/**
 * point:
 *   pay:
 *     status:
 *       pending: "PENDING"
 *       canceled: "CANCELED"
 *       confirmed: "CONFIRMED"
 *       rejected: "REJECTED"
 */
public enum PayPointStatus {
    PENDING, CANCELED, CONFIRMED, REJECTED, LOOKUP, NOEXIST;
}
