Feature: Tests crud operations for Food type

Background:
    * def createFoodPayload = read('classpath:payloads/create-food.json')

Scenario: Create a Food
    Given url 'http://localhost:8080/'
    And path 'food'
    And request createFoodPayload
    When method post
    Then status 200