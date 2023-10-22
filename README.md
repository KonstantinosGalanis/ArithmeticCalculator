# ArithmeticCalculator

This is an Arithmetic Calculator implemented in java using binary trees to calculate arithmetic expressions.

# Features 

This program  that evaluates any complex arithmetic expression which may contain:
1. **Parentheses:** indicate the priority of actions.
2. **The symbols of operations (binary operators):**
    - + (addition), (eg 3.3 + 2.2)
    - - (subtraction), (eg 3.3 - 2.2)
    -  x or * (multiplication), (eg 3.3 x 2.2 or 3.3 * 2.2)
    -  / (division), (eg 3.3 / 2.2)
    -  ^ (raise to power), (eg 3.3^2.2)
3. **Positive numbers (integer or floating point):** All calculations are done
between floating point numbers.
4. **Space characters ([ \t]) between parentheses, operators and numbers.**

An example of a complex numerical expression is the following:

```
5 + (((3.3 + 6.6) * 9.2 ) + 12.546) * 2.323 + ( ( ( 33.3 + 2342.1 ) * 55.555 ) - 10000.009 ) + 11.334 * 2.3 ^3.5
```

# Functionality

The program does the following:

1. Prints the message "Expression: " and reads a numeric expression from the
keyboard. 
2. Reads a string that consists of one of the following choices:
 - -s: prints to stdout “Postfix: ” and the equivalent expression in postfix format (of the equivalent Binary Tree).
 - -c: prints to stdout “Result: ” and the result of the numeric expression up to 6 decimal places.
 - -d: prints to stdout the equivalent expression in a format suitable for the dot program of the graphviz suite.
