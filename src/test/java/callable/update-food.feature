Feature: Callable feature that updates a food item

  Scenario: Update a food item
    Given url baseUrl
    And path 'food'
    And request __arg.request
    When method put
    Then status 200
