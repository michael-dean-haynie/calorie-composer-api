Feature: Callable feature that creates a food item

Scenario: Create a food item
    Given url 'http://localhost:8080/'
    And path 'food'
    And request __arg.request
    When method post
    Then status 200
