#ApacheCamel Demo

You'll find here a little demo about how ApacheCamel does work and some leads
to help you start your first project on camel.

This is actually the demo/example that I would like to have when I started
working with ApacheCamel a year ago (july 2016).

#Before Starting

Everything you need is in the Assets folder.
In there you'll find :
->An application config-sample
->The script to build the database used by the application
->The RabbitMq json config
->A copy of the xml that can be understand by SimpleFetch Module


##RabbitMq
To run this project you should have RabbitMq installed on your machine, to install
RabbitMq, check this link : https://www.rabbitmq.com/#getstarted

Don't forget to activate the ManagementPlugin by the way : https://www.rabbitmq.com/management.html

##Config
The config.properties sample that I gave you works only on my personal computer, so you'll have to change the value (you can keep the keys of course) to make it works on yours.
For the ftp config, you'll find every thing you need here : http://camel.apache.org/ftp.html

#Start
To start a module you just have to pass your config.properties file path
in argument, if you don't it will look at your root folder for file config.properties (useful when you just want to deploy your .jar)

For my development I'm using this path : camel-common/src/test/resources/config-dev.properties

Once this done, just run the module using your IDE.

