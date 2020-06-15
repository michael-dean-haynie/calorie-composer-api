Feature: Callable feature that deletes a food item

  Scenario: Delete a food item
    Given url 'http://localhost:8080/'
    And path 'food', __arg.id
    When method DELETE
    Then status 200
