# OOP Assignment 2  - Banking App (J. POO Morgan Chase & Co.)

<div align="center"><img src="https://tenor.com/view/piggy-bank-change-coin-bouncing-gif-11514793.gif" width="500px"></div>


## Project Structure

* org/poo/
    * fileio/ - contains classes used to read data from the input json files
    * utils/ - contains Utils class with methods for generating IBANs and card numbers
    * bank/ - contains all classes and packages pertaining to the assignment
        * account/
            * accountTypes/ - classic and savings accounts
            * Account - abstract class
            * AccountFactory - generates an account based on its type
            * AccountInfo - class used for storing information about the soon-to-be-created account
          * card/
              * cardTypes/ - classic and one-time cards
              * Card - abstract class
              * CardFactory - generates a card based on its type
              * CardInfo - class used for storing information about the to-be-created card
          * commands/ - contains all commands that can be interpreted by the program
          * transactions/ - package which helps with the implementation of transactions and processing them when writing
to output
          * bankDataBase - most important class in the program, it contains all information about the users, cards,
accounts and exchange rates
          * CommerciantInfo - class which helps with generating the spending report
          * Output - class with methods used for writing to output
          * User - the user (human)
    * main/
        * Main - the Main class runs the checker on my implementation


## Project Logic

* Implementation starts in Main. 
* * *
* The main logic of the program is the following:
    * Each input file has a varying amount of information give at input: there is the list of
users, the exchange rates and the commands. Not all the tests have he exchange rates. The
information regarding the users (and, if so be the case, for exchange rates) is read and the full
 list of users (and exchange rate conversion) is created.
    * After the user list and the exchange rates, the commands are written and processed
    * The commands can have different fields, which are vital when creating a transaction
    * Every variable that can be preset in the command will be read, even if it is not given
    * Based on the name of the command, the program calls statically implemented methods to execute the action
        * In the case of some commands, there is a possibility for an error to occur
        * Those errors are each checked, and then get printed to output or added to the transaction
list
* * *
* _addAccount_ and _createCard_ both work similarly. They take a specified email belonging to a user or an account IBAN and
create an account and a card respectively. At the same, time, to instantly check if the account or the card exist in the
database, they are mapped. The account to its IBAN and the card to its number. That way, there is no need for parsing
through the entire list of users to determine if an account or card exist or not.

* _addFunds_ adds a specific amount of money to an account (using its currency).

* _addInterest_ and _changeInterestRate_ are commands that only work for savings accounts. addInterest takes the existing
interest rate of the account and multiplies it with the amount of money in said account. That sum is added to the total.
changeInterestRate sets a new interest rate for said account.

* _checkCardStatus_ is a function which only returns an error if a change needs to happen but hasn't happened yet. If the
account is below its minimum balance, it won't become frozen until a checkCardStatus command is run on it. Also, this
command instantly has access to the card thanks to the HashMap implementation.

* _deleteAccount_ and deleteCard are pretty straight-forward. They delete an existing account or card respectively.

* _payOnline_ is a command which may need a conversion when it comes to currency. Using the exchange rates provided.

* _printTransactions_ outputs all transactions associated with a specific user.

* _printUsers_ lists all users in the banking database, providing detailed information about each one of them.

* _report_ creates a report for a specific account, detailing its balance, currency, IBAN, and filtered transaction history based on a given time range

* _sendMoney_ handles transactions by validating accounts, checking balances, managing currency conversions, and recording
the transfer details.

* _setAlias_ provides functionality to assign an alias to an existing account (identified by its IBAN).

* _setMinimumBalance_ sets a minimum balance to an existing account.

* _spendingsReport_ generates a detailed report of an account's spending activity within a specified time range It
provides insights into purchases made from specific commerciants and outputs the results in a structured format.

* _splitPayment_ is useful in scenarios where multiple account holders share a payment responsibility, such as group
expenses, ensuring fair distribution and detailed transaction tracking.


## Extra

Design patterns used:

* _Builder_ - for creating transactions

* _Factory_ - for creating different types of accounts and cards, but also to help with...

* _Strategy_ - for handling the transactions when they need to be written to output
