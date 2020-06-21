Feature: Callable feature that creates a tuna sandwich comboFood item

  Scenario: Create a tuna sandwich comboFood item
    # prepare constituent food payloads
    * def tunaPL = read('classpath:payloads/create-food-tuna.json')
    * def mayoPL = read('classpath:payloads/create-food-mayo.json')
    * def relishPL = read('classpath:payloads/create-food-relish.json')
    * def ryeBreadPL = read('classpath:payloads/create-food-rye-bread.json')

    # prepare args for create comboFood feature
    * def foods = ['#(tunaPL)', '#(mayoPL)', '#(relishPL)', '#(ryeBreadPL)']
    * copy partialComboFood = read('classpath:payloads/partial/create-combo-food-tuna-sandwich.json')
    * def args = { foods: '#(foods)', partialComboFood: '#(partialComboFood)'}
    When def result = call read('classpath:callable/crud/combofood/create-combo-food.feature') args
    * def tunaSandwich = result.comboFood
