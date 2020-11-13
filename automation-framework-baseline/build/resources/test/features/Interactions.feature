Feature: Interactions
  Scenario: Interaction #1
    Given I navigate to "Interactions Page"
    Then Page title is "ToolsQA"
    When I navigate to "Date Picker Page"
    When I set date of current month to 15
    Then I verify date value is set to 15