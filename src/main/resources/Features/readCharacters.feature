@Test1
Feature: As a developer, i would like to see all the character details from developer.marvel.com website

  @Test1_a
  Scenario: view all properties of the characters
    Given As an authorized user i call get characters service
    Then I view all properties of the characters

  @Test1_b
  Scenario: iterate all pages to view all characters
    Given As an authorised user i call get characters all page service
    Then I iterate all pages characters

  @Test2_a
  Scenario Outline: when sending invalid parameters, we should get valid error message
    Given As a user i set "<Invalid/Missing>" parameters to call get characters service
    Then I can see error message as "<Error_Message>" with "<Status_Code>"

  Examples:
    | Invalid/Missing     | Error_Message                                        | Status_Code |
    | Missing apikey      | You must provide a user key.                         | 409         |
    | Invalid Credentials | That hash, timestamp and key combination is invalid. | 401         |

