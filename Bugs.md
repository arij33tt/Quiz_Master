Q. What major bugs did I faced? (Severity:High)
1. When we run the image of posgres, the hibernate has set time zone to Asia-Calcutta. But the actual timezone should have been Asia-Kolkata. Only then canthe hibernate can connect to docker, otherwise timezone mismatch will occur. So, to fix this we add 

    "-Duser.timezone=Asia/Kolkata"

in the Vm setting , I was using intelij community version so I would say, Run/Debug configurations. 
To test if the timezone used is correct or not , there is a print function, at the start of the application. Which prints the time zone at the first line of console. Check and change accordingly.

2. The inconsistent querry problem. There are a few points using hibernate and jpa. First we has to have
    "spring.jpa.hibernate.ddl-auto=update"
in application.properties file. This will create the tables automatically(only if they do not exist) at the start of the application.
Now it creates a few problems, 
    2.1. Due to the nomenclature rule, name of the fields I used in entity class usually slightly differ from the respective column name in the querry 
    2.2. Not using nativeQuery=true, creates a mapping problem, with the Database
So the best option is when db is made automatically , then run the custom suerry and column names querry per table to ensure the thing you are recieving is correct.
----------------------------------------------------------------------------------------------------------------------
** Most of the bugs are the result of inconsistency in DTO and API naming , which was fixed eventually.Internal server error(500)

Q. How I looked for bugs?
I have searched for bugs at three places in this particular project
1. The console of intelij, presents us with stack trace,which shows us the problem and its origin. The prime source of finding/tracing issues in backend.
2. The developer mode, of the search engine, has a segment called network, which helps us in reading status code, headers , response and all. Which helps us to check if our backend is talking with frontend in an expected way or not. THis is prime source for debugging api communication between backend and frontend.
3. Adding print statement (or break point in the code). This is the best way to check if our data is flowing correctly or not, reaching its place or not, the shape of data is correct or not. This method really helps at any level of project.
----------------------------------------------------------------------------------------------------------------------
The functional bugs i faced and fixed.
1. Quizes are not sorted as per attempts left. And number attempt left is not showing.
2. Previous attempts did not show score , both in admin dashboard and user dashboard.

The functional bugs that are still existing.
1. Previous attempted question paper is not up for viewing.(Logic exists, but api doesn't)
2. Quiz can not be deleted. (to delete a quiz, No_Of_Attempts_Allowed= 0)
3. question can not be deleted (to delete a question , topic_id = deleted)
4. Filters to filter out quizes and attempts does not exist.
----------------------------------------------------------------------------------------------------------------------
