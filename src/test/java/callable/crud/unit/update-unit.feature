Feature: Callable feature that updates a unit item

  Scenario: Update a unit item
    Given url baseUrl
    And path 'unit'
    And request __arg.request
    When method put
    Then status 200
