# The Visitor Pattern

Based on the attached code (see commit this file was created), do the following tasks:

1. Create a class ExpressionExecutionVisitor that implements ExpressionVisitorInterface. This class should conform to
   the use in the Example class, and its use should result in the executed value of the expression.
2. Create a class ExpressionPrefixPrintingVisitor that implements ExpressionVisitorInterface. This class should result
   in a string (similar to ExpressionPrintingVisitor) that represents the expression in pre-fix, or Polish, notation.
   Update the Example class to print the result from ExpressionPrefixPrintingVisitor.
3. Write 200-300 words identifying additional data structures and operations (i.e., not the expression tree and print /
   execute operators) that the Visitor Design Pattern could be applied to. Make sure to include the benefits and
   detriments of your choice compared to operations embedded in the data structure elements.

The only modified or created files should be:

- Example.java
- ExpressionExecutionVisitor.java
- ExpressionPrefixPrintingVisitor.java

Only turn in these three files and your written response.
