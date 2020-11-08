Feature: Callable feature that creates a unit item

  Scenario: Create a unit item
    Given url baseUrl
    And path 'unit'
    And request __arg.request
    When method post
    Then status 201
