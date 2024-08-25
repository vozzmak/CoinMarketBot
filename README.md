# CoinMarketBot

CoinMarketBot is a simple Telegram bot that provides the current rate of cryptocurrency in USD using the CoinMarketCap API. This project is built with Java 22 and Spring Boot, and it leverages various dependencies to ensure smooth operation and potential future enhancements.

## Features

- **Get Cryptocurrency Rates**: The bot uses the CoinMarketCap API to fetch real-time cryptocurrency rates in USD.
- **Spring Boot Integration**: Built with Spring Boot for ease of development and deployment.
- **Extensible Design**: While security and database integration are not necessary for the current functionality, the project is designed to support them for future enhancements.

## Dependencies

The project uses the following dependencies:
- `spring-boot-starter-web`
- `lombok`
- `spring-boot-starter-security`
- `postgresql`
- `json`
- `docker-compose`
- `telegrambots`

## Prerequisites

To run this project locally, you will need:
- **Docker Desktop**: Ensure Docker is installed and running on your machine.
- **Telegram Bot Token**: Obtain a token for your bot by creating one with [@BotFather](https://t.me/BotFather) on Telegram.
- **CoinMarketCap API Key**: Register on [coinmarketcap.com](https://coinmarketcap.com/) to get your API key.

## Setup Instructions

1. **Clone the repository**:
2. **Configure Docker**:

Update the docker-compose.yml file according to your environment.
3. **Set up Application Properties**:

Navigate to src/main/resources/application.properties.
Add your bot name, bot token. 

Navigate to src/main/java/com.telegram.coinmarketbot/services/ApiService and add CoinMarketCap API key.

4. **Run the Application**:

Open the project in an IDE like IntelliJ IDEA.
Run the project and monitor the console for bot activities.
Open the bot in Telegram, you can get cryptucurrency rate in USD by sending the name or symbol of currency, for example Bitcoin or BTC(bot ignores case).

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Note
You won't find a detailed history of the project's development because it was initially developed in a private repository. The code published here omits sensitive information such as API keys.

## Inspiration

The project was inspired by this [Habr article](https://habr.com/ru/articles/715384/), which provides an overview of creating a similar bot.
