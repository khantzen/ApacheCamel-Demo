# ApacheCamel Demo

Here is a little demo about how ApacheCamel works and some leads to help you start your first project on camel.

The purpose of this demo app is to develop a _simple-fetch_ module which is going to recover a xml file containing user information (firstname, lastname, email, birthdate) from a ftp server, analyse this file and then store those informations into a database. All of this while sending log of its progression to another module called _audit-camel_ that will store them into the same database. Those two modules depend on a third module call _camel-common_ containing different classes used by both of them.

This is actually the demo/example that I would like to have when I started
working with ApacheCamel a year ago (july 2016).

Coucou Marion
C'est pour vérifier si tu lis bien

# Set up

Everything you need is in the _Assets_ folder.
What you'll find in it:
* An application config-sample
* The script to build the database used by the application
* The RabbitMq json config
* A copy of the xml that can be understand by SimpleFetch Module


## RabbitMq

To run this project you should have RabbitMq installed on your machine, to install
RabbitMq, check this link : _https://www.rabbitmq.com/#getstarted_

By the way, don't forget to activate the ManagementPlugin : _https://www.rabbitmq.com/management.html_ it will allow you to monitor your queues from a web interface.

## Config

The _config.properties_ sample that I gave you works only on my personal computer, so you'll have to change the values (you have to keep the keys of course) to make it works on yours.
For the ftp config, you'll find everything you need here : http://camel.apache.org/ftp.html

# Ready To Go

To start a module **don't forget to pass your config.properties file path
in argument**, if you don't the _ConfigManager_ will look at your root folder for file _config.properties_ (useful when you just want to deploy your .jar)

For my development I'm using this path : _camel-common/src/test/resources/config-dev.properties_

Once this is done, just run the module main class using your IDE.

## PS 
Unit test will come asap
