Feature: Callable feature that deletes a comboFood item

  Scenario: Delete a comboFood item
    Given url baseUrl
    And path 'comboFood', __arg.id
    When method delete
    Then status 200
