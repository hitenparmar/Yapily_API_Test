@Test2
Feature: As a developer, i would like to search for specific character with id, so that i can see available comics.

  @Test2_2a
  Scenario Outline: filter characters with id to get available comics
    Given As an authorised user i call get characters with "<CharacterID>"
    Then I see "<Properties>" parameter "<Parameter>" as "<NoOfAvailable>"

  Examples:
    | CharacterID |Properties | Parameter | NoOfAvailable |
    | 1010699     | comics    | available | 14            |

