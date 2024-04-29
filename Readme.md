# Bitcoin Price Notifier Telegram Bot

This project is a Telegram bot that notifies users about Bitcoin price changes. It's built with  Spring Boot, and Maven.
I used my last project crypto price tracker to get the current price of Bitcoin from the /currPriceBTC endpoint. You can reach the project from the link below:
[satas20/CryptoPriceTrackerApp](https://github.com/satas20/CryptoPriceTrackerApp)

## Features

- Set a notification for when the price of Bitcoin changes by specified threshold.
- Set a notification for when the price of Bitcoin reaches a specified price.
- Remove the price notification.
- Remove the change notification.
- Display the list of available commands.

## Commands

- `/help`: Display the list of available commands.
- `/setChangeNotification [threshold]`: Set a notification for when the price of Bitcoin changes by more than the specified threshold (in dollars).
- `/setPriceNotification [price]`: Set a notification for when the price of Bitcoin reaches the specified price (in dollars).
- `/removePriceNotification`: Remove the price notification.
- `/removeChangeNotification`: Remove the change notification.


# Preview


<p align="center">
  <img src="Media/profile.png" width="900">
</p>
<p align="center">
  <img src="Media/chatSS.png" width="900">
</p>



# How to Run 
Run the Cyrpto Price Tracker App  by satas20:

```bash
Follow setup steps for this project:
  https://github.com/satas20/CryptoPriceTrackerApp
```

Clone the repository:
```bash
git clone https://github.com/satas20/BitcoinPriceNotifierTelegramBot
```
Cd into the directory and open with your IDE:
```bash
cd BitcoinPriceNotifierTelegramBot
```

Run the project without executable jar file:
```bash
mvn spring-boot:run
```

Success! You are now running the app.
