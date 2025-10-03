To access the scripts: C:\Users\Carlo Carreon\eclipse-workspace\Exam\src\test\java\MainActions



For the Part 1 of the Technical Exam

1. I have used Playwright Java as my programming language and TestNG as my Automation Framework
2. For this approach, I have already extracted the locators of "Add To Cart" Buttons due to having multiple webelements having same ID
3. Since I have already extracted the locators, I simply proceeded to comparing it to the API Link.
4. My comparison logic for this is if there are matching button label in the API Link, It will output that Validation is successful.



For Part 2 of the Technical Exam

1. I notice that some web elements are unable to be clicked so I copy the Xpath of that element instead.
2. The step follows clicking the "Add to Cart" Button, getting the price, click the Continue to Cart button, then compare the price from the Sticky Cart vs the Selected Plan.
3. If both values are equal, Then it will output a message that the test succeeded.



