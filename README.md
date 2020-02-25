# Project information   
This project is the test-project for Infostroy Technologies Inc company


## Author
Ivaschenko Oleksandr

Email: 
oleksandr.ivashchenko20@gmail.com

## Installation

Step 1. Clone project from GitHub

Step 2. Install heroku util for deploy project to heroku

Step 3. Sign up in Heroku

Step 4. Go to project folder and run console (cmd)

Step 5. Write next commands

a)heroku login

b)git clone https://github.com/sowcraft/JSF_Test_Project.git

c)git push heroku master

d)heroku open

In the next type you can use this link https://test-jsf-questionnaire.herokuapp.com/login

## Test data

email - oleksandr.ivaschenko20@gmail.com

password - 123 
## Functionality

Users in this project have two roles(user,admin).  
User can make functions such as:

- Log in the site;
- Registration;
- Forgot password;
- Change password;
- Edit profile;
- Using Questionnaire;

Admin has all functions of User and :

- Show Response;
- Add and edit field;
- Remove field;

Additional functions:

- Forgot password(generate new password and send his to email);
- Encode (MD5) password;

## Main classes

- LoginBean - main bean for pages as Login, Registration, Change Password, Edit Profile and Forgot Password;

- FieldsBean - main bean for page Field;

- MainPageBean - main bean for page Questionnaire portal and Response;

- FieldDao - include all methods for FieldBean

- MainDao - include all methods for MainPageBean;

- UserDao - include all methods for LoginBean;

- User - entity class for users which contains description of variables of table User;

- MailUtil - set all properties for mail configuration and service for send email notifications;

- AuthorizationFilter - filter for unauthorized users;

- RoleFilter - filter pages for User and for ADMIN;

- package Validator - different type  validation for different pages; 

- JPAUtil - installation entity manager;

## Main methods

- saveUser() - method that can save user to db;

- addField() - method that add new Field to db;

- getEncoderPassword() - encode password;

- updateProfile() - method that checked input users data and update his;

- addAnswer() - add user answer on field to db;

## Main tools

- Java 8;
- JSF;
- Primefaces;
- Postgres SQL;
- JavaScript;









