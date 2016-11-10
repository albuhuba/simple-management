# A simple SpringBoot application

It has got 2 endpoints one for storing a sinmple json message and one to retrieve all of them. Additionally in the same time before it is saved, through a dispatcher (websockets) it sends to the registered clients the added message.

I haven't included any endpoints in this description, it is visible in the code and it is not that hard to find it.

It uses websockets to send simple messages to the subscribed clients and shows the raw text in a list view.

The SpringBoot is the basis of the application and on top of that a couple of frameworks are working together to fulfill the soul of this project. Dor data storage I've used HIbernate 5 without the JPA2 Criteria api and the validations from this framework. To ensure that the database is in a correct state it is configured to validate at startup. Also in the same time not to have migration problems, I am using Flyway by boxfuse. After executing an initial script, the update scripts are taken from the classpath, from a specific location.

For documentation I've used Swagger 2 with the UI, which can be viewd under ../docs. For ogging I've choosed the log4j with the proper configuration. The spring boot is configured via a YML file, which can be externalized further. I am using the HibernateTransactionManager as the default transaction manager in the spring environment.

There are a couple of tests, but additionally for integration test the rest-assured framework can be used. It works well with the spring-boot. Also a test configuration can be used in order to have a separate behavior and separate connection to an alternative database.

To enrich/make it more scalable the application, I could introduce a queuing system in another application, which is backed with a proper message broker with persistent storage, we could boost the rest API and even if it can't consume in time the requests to create new messages, the messages would be stored and they could be replayed if the system fails. Also with this implementation we would not loose data.

On the other hand since it is a restful API, in theory we could have read nodes and write nodes additionally multiple API's too, but this would mean that we might miss some information or it will not come in time.
