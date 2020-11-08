Feature: Callable feature that reads a unit item

  Scenario: Read a unit item
    Given url baseUrl
    And path 'unit', __arg.id
    When method get
    Then status 200