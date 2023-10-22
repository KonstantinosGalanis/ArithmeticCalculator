package ce326.hw1;

import java.util.Scanner;
import java.util.regex.*;

class TreeNode {
    char operator;
    double value;
    TreeNode left;
    TreeNode right;

    TreeNode(char operator) {
        this.operator = operator;
        this.left = null;
        this.right = null;
    }

    TreeNode(double value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

public class ArithmeticCalculator {
    TreeNode root;
    private static int counter = 0;

    ArithmeticCalculator(String expression) {
        root = createTree(expression);
    }
    
    public String removeUnbalancedParenthesis(String expression) {
        int openParenCount = 0;
        StringBuilder result = new StringBuilder();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                openParenCount++;
            } else if (ch == ')' && openParenCount > 0) {
                openParenCount--;
            } else if (ch == ')' && openParenCount == 0) {
                continue;
            }
            result.append(ch);
        }
        for (int i = result.length() - 1; i >= 0 && openParenCount > 0; i--) {
            if (result.charAt(i) == '(') {
                result.deleteCharAt(i);
                openParenCount--;
            }
        }
        return result.toString();
    }
    
    private TreeNode createTree(String expression) {
        if(expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1,expression.length()-1);
        }
        expression = removeUnbalancedParenthesis(expression);
        int lowestPriorityOperatorIndex = -1;
        int temp_lowestPriorityOperatorIndex = -1;
        int priority = 10; 
        int temp_priority = 10;
        int bracketCount = 0;
        int helper = 40;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == ')') {
                bracketCount++;
            } 
            else if (c == '(') {
                bracketCount--;
            } 
            else if (bracketCount == 0 && (c == '+' || c == '-') && priority > 1) {
                priority = 1;
                lowestPriorityOperatorIndex = i;
            } 
            else if (bracketCount == 0 && (c == '*' || c == '/' || c == 'x') && priority > 2) {
                priority = 2;
                lowestPriorityOperatorIndex = i;
            }
            else if (bracketCount == 0 && (c == '^') && priority > 3) {
                priority = 3;
                lowestPriorityOperatorIndex = i;
            }
            else if (bracketCount != 0) {
                if (bracketCount < helper && (c == '+' || c == '-') && temp_priority >= 1) {
                    temp_priority = 1;
                    helper = bracketCount;
                    temp_lowestPriorityOperatorIndex = i;
                } 
                else if (bracketCount < helper && (c == '*' || c == '/' || c == 'x') && temp_priority >= 2) {
                    temp_priority = 2;
                    helper = bracketCount;
                    temp_lowestPriorityOperatorIndex = i;
                }
                else if (bracketCount < helper && (c == '^') && temp_priority >= 3) {
                    temp_priority = 3;
                    helper = bracketCount;
                    temp_lowestPriorityOperatorIndex = i;
                }
            }
            
        }

        if (lowestPriorityOperatorIndex == -1 && temp_lowestPriorityOperatorIndex == -1){
            expression = expression.replaceAll("\\(|\\)", "");
            return new TreeNode(Double.parseDouble(expression));
        }
        
        if (lowestPriorityOperatorIndex != -1) {
            TreeNode node = new TreeNode(expression.charAt(lowestPriorityOperatorIndex));
            String leftExpression = expression.substring(0, lowestPriorityOperatorIndex).trim();
            String rightExpression = expression.substring(lowestPriorityOperatorIndex + 1).trim();
            node.left = createTree(leftExpression);
            node.right = createTree(rightExpression);
             return node;
        }
        else {
            TreeNode node = new TreeNode(expression.charAt(temp_lowestPriorityOperatorIndex));
            String leftExpression = expression.substring(0, temp_lowestPriorityOperatorIndex).trim();
            String rightExpression = expression.substring(temp_lowestPriorityOperatorIndex + 1).trim();
            node.left = createTree(leftExpression);
            node.right = createTree(rightExpression);
            return node;
        }

       
    }
    
    private static void toDotString(TreeNode root, StringBuilder helper) {
        if (root == null) {
            return ;
        }
        
        helper.append("    node").append(root.hashCode()).append(" [label=\"");
        if (root.operator != '\0') {
            helper.append(root.operator);
        } else {
            helper.append(root.value);
        }
        helper.append("\"]\n");
        
        if (root.left != null) {
            helper.append("    node").append(root.hashCode()).append(" -> node").append(root.left.hashCode()).append("\n");
            toDotString(root.left, helper);
        }
        
        if (root.right != null) {
            helper.append("    node").append(root.hashCode()).append(" -> node").append(root.right.hashCode()).append("\n");
            toDotString(root.right, helper);
        }
    }
    
    public String toDotString() {
       if (root == null) {
            return "";
        }
        StringBuilder helper = new StringBuilder();
        helper.append("digraph BinaryTree {\n");
        toDotString(root, helper);
        helper.append("}\n");
        return helper.toString();
    }

    private void toString(TreeNode root,StringBuilder helper) {
        if (root == null) {
            return;
        }

        toString(root.left, helper);

        toString(root.right, helper);

        if (root.operator != '\0') {
            helper.append(root.operator).append(" ");
        } 
        else {
            double doubleValue = root.value;
            int intValue = (int) doubleValue;
            if (doubleValue == intValue) {
                helper.append(intValue).append(" ");
            } else {
                helper.append(doubleValue).append(" ");
            }
        }
    }
    
    public String toString() {
       if (root == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        toString(root, result);
        return result.toString().trim();
    }

    private double calculate(TreeNode node) {
        if (node.left == null && node.right == null) {
            return node.value;
        }

        double leftValue = calculate(node.left);
        double rightValue = calculate(node.right);

        switch (node.operator) {
            case '+':
                return leftValue + rightValue;
            case '-':
                return leftValue - rightValue;
            case '*':
                return leftValue * rightValue;
            case '/':
                return leftValue / rightValue;
            case 'x':
                return leftValue * rightValue;
            case '^':
                return Math.pow(leftValue, rightValue);
        }

        return 0;
    }
    
    double calculate() {
	return calculate(root);	
    }
    
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == 'x' || c == '^';
    }
    
    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Expression: ");

        String expression = new String(scanner.nextLine().trim());
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (!Character.isDigit(c) && c != '.' && c != ' ' && c != '+' && c != '-' && c != '*' && c != '/' && c != 'x' && c != '^' && c != '(' && c != ')') {
                System.out.println("\n[ERROR] Invalid character");
                System.exit(0);
            }
        }

       Pattern pattern = Pattern.compile("\\d\\s+\\d");
       Matcher matcher = pattern.matcher(expression);

        if (matcher.find()) {
          System.out.println("\n[ERROR] Consecutive operands");
          System.exit(0);
        }
  
        expression = expression.replaceAll("\\s", "");
        
        if (expression.charAt(0) == '+' || expression.charAt(expression.length() - 1) == '+'//4
            || expression.charAt(0) == '-' || expression.charAt(expression.length() - 1) == '-'
            || expression.charAt(0) == '*' || expression.charAt(expression.length() - 1) == '*'
            || expression.charAt(0) == '/' || expression.charAt(expression.length() - 1) == '/'
            || expression.charAt(0) == '^' || expression.charAt(expression.length() - 1) == '^'
            || expression.charAt(0) == 'x' || expression.charAt(expression.length() - 1) == 'x') {
            
            System.out.println("\n[ERROR] Starting or ending with operator");
            System.exit(0);
        }
        
        int openedParentheses = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                openedParentheses++;
            } 
            else if (c == ')') {
                if (openedParentheses > 0) {
                    openedParentheses--;
                }
                else {
                    System.out.println("\n[ERROR] Closing unopened parenthesis");
                    System.exit(0);
                }
            }
        }
        
        if (openedParentheses > 0) {
            System.out.println("\n[ERROR] Not closing opened parenthesis");
            return;
        }

        for (int i = 0; i < expression.length() - 1; i++) {
            char currentChar = expression.charAt(i);
            char nextChar = expression.charAt(i + 1);
            if (isOperator(currentChar) && isOperator(nextChar)) {
                System.out.println("\n[ERROR] Consecutive operators");
                System.exit(0);
            }
        }
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isOperator(c)) {
                if (i == 0 || expression.charAt(i-1) == '(') {
                    System.out.println("\n[ERROR] Operator appears after opening parenthesis");
                    return;
                }
                if (i == expression.length() - 1 || expression.charAt(i+1) == ')') {
                    System.out.println("\n[ERROR] Operator appears before closing parenthesis");
                    return;
                }
            }
        }
        pattern = Pattern.compile("\\)\\(");  // create a pattern to match the string ")("
        matcher = pattern.matcher(expression);
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                if (i > 0 && !isOperator(expression.charAt(i - 1)) && expression.charAt(i - 1) != '(') {
                    System.out.println("\n[ERROR] Operand before opening parenthesis");
                    System.exit(0);
                }
            } 
            else if (ch == ')') {
                if (matcher.find()) {
                   System.out.println("\n[ERROR] ')' appears before opening parenthesis");
                   System.exit(0);
                }
                if (i < expression.length() - 1 && !isOperator(expression.charAt(i + 1)) && expression.charAt(i + 1) != ')') {
                   System.out.println("\n[ERROR] Operand after closing parenthesis");
                   System.exit(0);
               }
           }
       }
        
        ArithmeticCalculator root = new ArithmeticCalculator(expression);
        String choice = scanner.next();
        System.out.print("\n");
        switch (choice) {
             case "-d":
                 String result = new String();
                 result = root.toDotString();
                 System.out.println(result);
                 break;
             case "-s":
                 String postfix = new String();
                 postfix = root.toString();
                 System.out.println("Postfix: "+postfix);
                 break;
             case "-c":
                 double calculation;
                 calculation = root.calculate();
                 System.out.printf("Result: %.6f",calculation);
                 break;
         }
        System.out.print("\n");
    }   
}
