Feature: Tests crud operations for Food type

Background:
    # Empty for now

Scenario: Create a Food
    * def food = {}
    * url 'http://localhost:8080/'
    * path 'food'
    * request {}
    * method post
    * status 200