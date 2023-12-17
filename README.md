# Calculator
This project has been created as part of an interview exercise. The following Rest API is based on the documentation given as statement.


## Run project
To run project, please, use the command _"mvn package"_ to build the project and execute all tests. In order to skip the test, please, execute the command _"mvn package -Dmaven.test.skip=true"_
In case of error, please, use the maven plugin in intelliJ to execute mannually each command.

## Postman collection
The collection used to test in local the whole project could be found in _src/test/resources/Interview.postman_collection.json_
This collection is based on the inputs and outputs provided in the statement. Some bodies in each request should be change in order to cover all the steps(i.e: the method _missing_numbers_ needs execute as many as  number of order given)

## Mutation test
As an addition, I wanted to add mutation testing to the project. This kind of test comes in handy for this kind of services where the result depends on the input.
To execute the mutation test, please, use the command _"mvn test-compile org.pitest:pitest-maven:mutationCoverage"_.
In case of error, please, use the Pitest plugin(_piest:mutationCoverage_) in intelliJ to execute mannually each command.
After execute the plugin, a report should be created at target level.
The associated coverage after the last commit is as follows:

![imagen](https://github.com/AdrinocDev/interview/assets/57809713/1956722e-66f2-4622-a8a3-ec8a53f997a9)

## Remarks
1. The endpoints are based on the information in the statement. Some steps have been taken for granted and others have been confirmed by questions to the interviewer.
2. Some tests must be performed respecting certain requirements such as the _calculate_ method must receive non-repeated and positive elements greater than 0.Another case is the _missing_numbers_ method where the specified order must meet a minimum number of executions of the calculate method. i.e.: if the calculate method is executed twice and then _missing_number(3)_ is executed, we will get an error as output.
3. Both the tests and the mutation tests have been generated with base cases or happy path in mind. There are many other cases that can be taken into account and that would improve the current coverage shown in the image above.
4. Some points for improvement could have been taken into account such as error handling control to cover the requirements described in point 2.
