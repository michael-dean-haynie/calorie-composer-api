@ignore
Feature: Utilities

    Scenario: Utilities
      * def utils = {}

      # Random String
      * set utils.rs = function(len){ return (java.util.UUID.randomUUID() + '').slice(len * -1)}

      # Random Decimal (xx.xx)
      * set utils.rd = function(){ return Math.floor(Math.random() * 10000) / 100 }

      * set utils.getById = function(array, id){ return array.find(function(item){return item.id === id}); }