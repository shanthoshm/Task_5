package com.banking.account.entity;

import com.banking.account.enumeration.AccountStatus;
import com.banking.account.enumeration.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore // Excluding the "id" field from JSON representation
    private Integer id;
    @JsonProperty("accountNumber")
    private Long accountNumber;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("street")
    private String street;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @Enumerated(EnumType.STRING)
    @JsonProperty("accountType")
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    @JsonProperty("accountStatus")
    private AccountStatus accountStatus;
    @JsonProperty("balance")
    private Double balance;
}
