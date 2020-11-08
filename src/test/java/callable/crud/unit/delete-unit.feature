Feature: Callable feature that deletes a unit item

  Scenario: Delete a unit item
    Given url baseUrl
    And path 'unit', __arg.id
    When method delete
    Then status 200
