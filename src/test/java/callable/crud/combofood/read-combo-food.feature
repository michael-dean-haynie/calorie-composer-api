Feature: Callable feature that reads a comboFood item

  Scenario: Read a comboFood item
    Given url baseUrl
    And path 'comboFood', __arg.id
    When method get
    Then status 200