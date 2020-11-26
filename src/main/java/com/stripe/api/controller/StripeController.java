package com.stripe.api.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.ws.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.Stripe;
import com.stripe.api.StripeConfig;
import com.stripe.api.StripeRestAPIConstants;
import com.stripe.api.service.StripeService;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.ApiException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.StripeError;
import com.stripe.model.StripeErrorResponse;
import com.stripe.model.Token;
import com.stripe.net.StripeResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class StripeController {
	
	private static final Logger logger = LoggerFactory.getLogger(StripeController.class);
		
	@Autowired
	private StripeService stripePaymentService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/*
	 * Endpoint to charge the card thorugh Strip API.
	 * */
	@RequestMapping(value = StripeRestAPIConstants.CREATE_STRIPE_PAYMENT, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Charge charge(Locale locale, Model model) throws AuthenticationException, InvalidRequestException, ApiConnectionException, CardException, ApiException, StripeException {
		
		Charge charge = null;
		
		try {
			charge = stripePaymentService.charge();
		} catch (AuthenticationException ex) { 
			
			Charge chargeAuthenticationException = new Charge();
			
			chargeAuthenticationException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeAuthenticationException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeAuthenticationException;
		} catch (InvalidRequestException ex) {
			Charge chargeInvalidRequestException = new Charge();
			
			chargeInvalidRequestException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeInvalidRequestException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeInvalidRequestException;
		} catch (ApiConnectionException ex) {
			Charge chargeApiConnectionException = new Charge();
			
			chargeApiConnectionException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeApiConnectionException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeApiConnectionException;
		} catch (CardException ex) {
			Charge chargeCardException = new Charge();
			
			chargeCardException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeCardException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeCardException;
		} catch (ApiException ex) {
			Charge chargeApiException = new Charge();
			
			chargeApiException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeApiException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeApiException;
		} catch (StripeException ex) {
			Charge chargeStripeException = new Charge();
			
			chargeStripeException.setFailureCode(ex.getStripeError().getDeclineCode());
			chargeStripeException.setFailureMessage(ex.getStripeError().getMessage());
			return chargeStripeException;
		}
		
		return charge;
	}
}