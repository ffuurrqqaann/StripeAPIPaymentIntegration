package com.stripe.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/*
 * A configuration class to load the configurartions from config.properties file from the application resources folder. 
 * */

@Configuration
@PropertySource("classpath:config.properties")
public class StripeConfig {
	
	@Value("${stripe.apikey}")
	private String stripeApiKey;
	
	@Value("${stripe.secret.key}")
	private String stripeSecretKey;
	
	@Value("${stripe.checkout.amount}")
	private String stripeCheckoutAmount;
	
	@Value("${stripe.checkout.currency}")
	private String stripeCheckoutCurrency;
	
	@Value("${cc.number}")
	private String ccNumber;
	
	@Value("${cc.exp_month}")
	private int ccExpiryMonth;
	
	@Value("${cc.exp_year}")
	private int ccExpiryYear;
	
	@Value("${cc.cvc}")
	private String ccCVC;
	
	//stripe getters.
	public String getStripeApiKey() {
		return stripeApiKey;
	}
	public String getStripeSecretKey() {
		return stripeSecretKey;
	}
	
	public String getStriptCheckoutAmount() {
		return stripeCheckoutAmount;
	}
	public String getStripeCheckoutCurrency() {
		return stripeCheckoutCurrency;
	}
	
	//credit card getters
	public String getCcNumber() {
		return ccNumber;
	}
	public int getExpiryMonth() {
		return ccExpiryMonth;
	}
	
	public int getExpiryYear() {
		return ccExpiryYear;
	}
	public String getCVC() {
		return ccCVC;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}