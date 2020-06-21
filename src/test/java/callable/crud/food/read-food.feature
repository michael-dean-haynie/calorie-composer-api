Feature: Callable feature that reads a food item

  Scenario: Read a food item
    Given url baseUrl
    And path 'food', __arg.id
    When method get
    Then status 200