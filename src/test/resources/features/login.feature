
  Feature: login
    Background:
      Given the user is on the login page

    Scenario Outline: Login attempt with different credentials
      When the user attempts to login with  username "<username>" and password "<password>"
      Then the result should be "<expectedResult>"

      Examples:
      | username        | password     | expectedResult                                                            |
      | standard_user   | secret_sauce | inventory page displayed                                                  |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | secret_sauce | Epic sadface: Username and password do not match any user in this service |