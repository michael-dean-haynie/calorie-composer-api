Feature: Callable feature that creates a food item

Scenario: Create a food item
    Given url baseUrl
    And path 'food'
    And request __arg.request
    When method post
    Then status 201
