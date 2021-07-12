package com.bank.account.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * the Account entity - Table Account
 * @author sazaidi
 *
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="Account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    private double amount;
    

    @Builder.Default
    @Temporal(TemporalType.DATE)
    private Date date = new Date();
    
    @Builder.Default
    private double allowNegativeAmount = -500;


}
