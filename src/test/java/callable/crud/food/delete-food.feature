Feature: Callable feature that deletes a food item

  Scenario: Delete a food item
    Given url baseUrl
    And path 'food', __arg.id
    When method delete
    Then status 200
