package com.stripe.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.api.StripeConfig;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.ApiException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

@Service
public class StripeService {
	
	@Autowired
	private StripeConfig stripeConfig; 
	
	@PostConstruct
	public void init() {
		Stripe.apiKey= stripeConfig.getStripeSecretKey();
	}
	
	public Token getStripePaymentToken() throws StripeException {
		Map<String, Object> card = new HashMap<String, Object>();
		card.put("number", stripeConfig.getCcNumber());
		card.put("exp_month", stripeConfig.getExpiryMonth());
		card.put("exp_year", stripeConfig.getExpiryYear());
		card.put("cvc", stripeConfig.getCVC());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card", card);

		return Token.create(params);
	}
	
	public Charge charge() throws AuthenticationException, InvalidRequestException, ApiConnectionException, CardException, ApiException, StripeException {
		
		
		System.out.println();
		System.out.println();
		System.out.println("Token received = "+getStripePaymentToken().getId());
		System.out.println();
		System.out.println();
		
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		
		chargeParams.put("amount", stripeConfig.getStriptCheckoutAmount());
		chargeParams.put("currency", stripeConfig.getStripeCheckoutCurrency());
		chargeParams.put("description", "test description.");
		chargeParams.put("source", getStripePaymentToken().getId());
		
		return Charge.create(chargeParams);
	}
	
}
