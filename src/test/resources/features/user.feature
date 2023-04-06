Feature: User - register and login

  Scenario Outline: Sign up account
    Given I open the browser
    Given I have accepted cookies
    Given I input email "<email>"
    Given I input username "<username>"
    Given I input password "<password>"
    When I press sign up
    Then I made an account "<result>"

    Examples:
      | email          | username   | password  | result |
      | random12309qwe0q9123456@gmail.com | randomqwe019i3019je01456 | 1Password!321 | success |
      | random12309qwe0q9123456@gmail.com | randomqwe019i3019je01randomqwe019i3019je01randomqwe019i3019je01randomqwe019i3019je01randomqwe019i3019je01randomqwe019i3019je01 | 1Password!321 | fail |
      | random12309qwe0q9123456@gmail.com | randomqwe019i3019je01456 | 1Password!321 | fail |
      |                | randomqwe019i3019je013123 | 1Password!321 | fail |

