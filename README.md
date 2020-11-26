# StripeAPIPaymentIntegration
This application consumes stripe api to deduct money from the cards.

This REST API is build using following tools and technologies.

1. SpringMVC
2. Maven
3. Stripe 19.24.0
4. Spring Tool Suite IDE

In this application, the amount from the card is deducted through stripe api. The stripe api version 19.24.0 has been used and the maven dependency has been added into the pom.xml file.
There is a separate config.properties file has been created which contains the config parameters i.e., stripe api key, stripe secret, checkout amount and currency to be deducted, etc.
