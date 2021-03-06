/*
 * Copyright 2012 Udo Klimaschewski
 * 
 * http://UdoJava.com/
 * http://about.me/udo.klimaschewski
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.upseil.gdx.util;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * <h1>EvalEx - Java Expression Evaluator</h1>
 * 
 * <h2>Introduction</h2> EvalEx is a handy expression evaluator for Java, that
 * allows to evaluate simple mathematical and boolean expressions. <br>
 * Key Features:
 * <ul>
 * <li>Uses double for calculation and result</li>
 * <li>Single class implementation, very compact</li>
 * <li>No dependencies to external libraries</li>
 * <li>Precision and rounding mode can be set</li>
 * <li>Supports variables</li>
 * <li>Standard boolean and mathematical operators</li>
 * <li>Standard basic mathematical and boolean functions</li>
 * <li>Custom functions and operators can be added at runtime</li>
 * </ul>
 * <br>
 * <h2>Examples</h2>
 * 
 * <pre>
 *  double result = null;
 *  
 *  Expression expression = new Expression("1+1/3");
 *  result = expression.eval():
 *  expression.setPrecision(2);
 *  result = expression.eval():
 *  
 *  result = new Expression("(3.4 + -4.1)/2").eval();
 *  
 *  result = new Expression("SQRT(a^2 + b^2").with("a","2.4").and("b","9.253").eval();
 *  
 *  double a = new double("2.4");
 *  double b = new double("9.235");
 *  result = new Expression("SQRT(a^2 + b^2").with("a",a).and("b",b).eval();
 *  
 *  result = new Expression("2.4/PI").setPrecision(128).setRoundingMode(RoundingMode.UP).eval();
 * 
 *  result = new Expression("not(x<7 || sqrt(max(x,9)) <= 3))").with("x","22.9").eval();
 * </pre>
 * 
 * <br>
 * <h2>Supported Operators</h2>
 * <table>
 * <tr>
 * <th>Mathematical Operators</th>
 * </tr>
 * <tr>
 * <th>Operator</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>+</td>
 * <td>Additive operator</td>
 * </tr>
 * <tr>
 * <td>-</td>
 * <td>Subtraction operator</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>Multiplication operator</td>
 * </tr>
 * <tr>
 * <td>/</td>
 * <td>Division operator</td>
 * </tr>
 * <tr>
 * <td>%</td>
 * <td>Remainder operator (Modulo)</td>
 * </tr>
 * <tr>
 * <td>^</td>
 * <td>Power operator</td>
 * </tr>
 * </table>
 * <br>
 * <table>
 * <tr>
 * <th>Boolean Operators<sup>*</sup></th>
 * </tr>
 * <tr>
 * <th>Operator</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>=</td>
 * <td>Equals</td>
 * </tr>
 * <tr>
 * <td>==</td>
 * <td>Equals</td>
 * </tr>
 * <tr>
 * <td>!=</td>
 * <td>Not equals</td>
 * </tr>
 * <tr>
 * <td>&lt;&gt;</td>
 * <td>Not equals</td>
 * </tr>
 * <tr>
 * <td>&lt;</td>
 * <td>Less than</td>
 * </tr>
 * <tr>
 * <td>&lt;=</td>
 * <td>Less than or equal to</td>
 * </tr>
 * <tr>
 * <td>&gt;</td>
 * <td>Greater than</td>
 * </tr>
 * <tr>
 * <td>&gt;=</td>
 * <td>Greater than or equal to</td>
 * </tr>
 * <tr>
 * <td>&amp;&amp;</td>
 * <td>Boolean and</td>
 * </tr>
 * <tr>
 * <td>||</td>
 * <td>Boolean or</td>
 * </tr>
 * </table>
 * *Boolean operators result always in a double value of 1 or 0 (zero). Any
 * non-zero value is treated as a _true_ value. Boolean _not_ is implemented by
 * a function. <br>
 * <h2>Supported Functions</h2>
 * <table>
 * <tr>
 * <th>Function<sup>*</sup></th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>NOT(<i>expression</i>)</td>
 * <td>Boolean negation, 1 (means true) if the expression is not zero</td>
 * </tr>
 * <tr>
 * <td>IF(<i>condition</i>,<i>value_if_true</i>,<i>value_if_false</i>)</td>
 * <td>Returns one value if the condition evaluates to true or the other if it
 * evaluates to false</td>
 * </tr>
 * <td>MIN(<i>e1</i>,<i>e2</i>, <i>...</i>)</td>
 * <td>Returns the smallest of the given expressions</td>
 * </tr>
 * <tr>
 * <td>MAX(<i>e1</i>,<i>e2</i>, <i>...</i>)</td>
 * <td>Returns the biggest of the given expressions</td>
 * </tr>
 * <tr>
 * <td>CLAMP(<i>expression</i>,<i>minimum</i>,<i>maximum</i>)</td>
 * <td>Clamps the result of the expression between the minimum and the maximum.</td>
 * </tr>
 * <tr>
 * <td>ABS(<i>expression</i>)</td>
 * <td>Returns the absolute (non-negative) value of the expression</td>
 * </tr>
 * <tr>
 * <td>ROUND(<i>expression</i>,precision)</td>
 * <td>Rounds a value to a certain number of digits, uses the current rounding
 * mode</td>
 * </tr>
 * <tr>
 * <td>FLOOR(<i>expression</i>)</td>
 * <td>Rounds the value down to the nearest integer</td>
 * </tr>
 * <tr>
 * <td>CEILING(<i>expression</i>)</td>
 * <td>Rounds the value up to the nearest integer</td>
 * </tr>
 * <tr>
 * <td>LOG(<i>expression</i>)</td>
 * <td>Returns the natural logarithm (base e) of an expression</td>
 * </tr>
 * <tr>
 * <td>LOG10(<i>expression</i>)</td>
 * <td>Returns the common logarithm (base 10) of an expression</td>
 * </tr>
 * <tr>
 * <td>SQRT(<i>expression</i>)</td>
 * <td>Returns the square root of an expression</td>
 * </tr>
 * <tr>
 * <td>SIN(<i>expression</i>)</td>
 * <td>Returns the trigonometric sine of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>COS(<i>expression</i>)</td>
 * <td>Returns the trigonometric cosine of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>TAN(<i>expression</i>)</td>
 * <td>Returns the trigonometric tangens of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ASIN(<i>expression</i>)</td>
 * <td>Returns the angle of asin (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ACOS(<i>expression</i>)</td>
 * <td>Returns the angle of acos (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ATAN(<i>expression</i>)</td>
 * <td>Returns the angle of atan (in degrees)</td>
 * </tr>
 * <tr>
 * <td>SINH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic sine of a value</td>
 * </tr>
 * <tr>
 * <td>COSH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic cosine of a value</td>
 * </tr>
 * <tr>
 * <td>TANH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic tangens of a value</td>
 * </tr>
 * <tr>
 * <td>RAD(<i>expression</i>)</td>
 * <td>Converts an angle measured in degrees to an approximately equivalent
 * angle measured in radians</td>
 * </tr>
 * <tr>
 * <td>DEG(<i>expression</i>)</td>
 * <td>Converts an angle measured in radians to an approximately equivalent
 * angle measured in degrees</td>
 * </tr>
 * </table>
 * *Functions names are case insensitive. <br>
 * <h2>Supported Constants</h2>
 * <table>
 * <tr>
 * <th>Constant</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>e</td>
 * <td>The value of <i>e</i>, exact to 70 digits</td>
 * </tr>
 * <tr>
 * <td>PI</td>
 * <td>The value of <i>PI</i>, exact to 100 digits</td>
 * </tr>
 * <tr>
 * <td>TRUE</td>
 * <td>The value one</td>
 * </tr>
 * <tr>
 * <td>FALSE</td>
 * <td>The value zero</td>
 * </tr>
 * </table>
 * 
 * <h2>Add Custom Operators</h2>
 * 
 * Custom operators can be added easily, simply create an instance of
 * `Expression.Operator` and add it to the expression. Parameters are the
 * operator string, its precedence and if it is left associative. The operators
 * `eval()` method will be called with the double values of the operands.
 * All existing operators can also be overridden. <br>
 * For example, add an operator `x >> n`, that moves the decimal point of _x_
 * _n_ digits to the right:
 * 
 * <pre>
 * Expression e = new Expression("2.1234 >> 2");
 * 
 * e.addOperator(e.new Operator(">>", 30, true) {
 *     {@literal @}Override
 *     public double eval(double v1, double v2) {
 *         return v1.movePointRight(v2.toint().intValue());
 *     }
 * });
 * 
 * e.eval(); // returns 212.34
 * </pre>
 * 
 * <br>
 * <h2>Add Custom Functions</h2>
 * 
 * Adding custom functions is as easy as adding custom operators. Create an
 * instance of `Expression.Function`and add it to the expression. Parameters are
 * the function name and the count of required parameters. The functions
 * `eval()` method will be called with a list of the double parameters. All
 * existing functions can also be overridden. <br>
 * A <code>-1</code> as the number of parameters denotes a variable number of arguments.<br>
 * For example, add a function `average(a,b,c)`, that will calculate the average
 * value of a, b and c: <br>
 * 
 * <pre>
 * Expression e = new Expression("2 * average(12,4,8)");
 * 
 * e.addFunction(e.new Function("average", 3) {
 *     {@literal @}Override
 *     public double eval(double[] parameters) {
 *         double sum = parameters.get(0).add(parameters.get(1)).add(parameters.get(2));
 *         return sum.divide(new double(3));
 *     }
 * });
 * 
 * e.eval(); // returns 16
 * </pre>
 * 
 * The software is licensed under the MIT Open Source license (see LICENSE
 * file). <br>
 * <ul>
 * <li>The *power of* operator (^) implementation was copied from [Stack
 * Overflow
 * ](http://stackoverflow.com/questions/3579779/how-to-do-a-fractional-power
 * -on-double-in-java) Thanks to Gene Marin</li>
 * <li>The SQRT() function implementation was taken from the book [The Java
 * Programmers Guide To numerical
 * Computing](http://www.amazon.de/Java-Number-Cruncher
 * -Programmers-Numerical/dp/0130460419) (Ronald Mak, 2002)</li>
 * </ul>
 * 
 * @author Udo Klimaschewski (http://about.me/udo.klimaschewski)
 * @author Frederik Petersen and Lennart Hensler:
 * <br/>
 * Heavily edited for performance reason. Some parts (like IF) have been removed.
 * Primitive double used instead of BigDecimal.
 */
public class DoubleBasedExpression {

    /**
     * Definition of PI as a constant, can be used in expressions as variable.
     */
    public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679;
            
    /**
     * Definition of e: "Euler's number" as a constant, can be used in
     * expressions as variable.
     */
    public static final double e = 2.71828182845904523536028747135266249775724709369995957496696762772407663;

    /**
     * The characters (other than letters and digits) allowed as the first character in a variable.
     */
    private String firstVarChars = "_";

    /**
     * The characters (other than letters and digits) allowed as the second or subsequent characters in a variable.
     */
    private String varChars = "_";

    /**
     * The current infix expression, with optional variable substitutions.
     */
    private final String expression;

    /**
     * All defined operators with name and implementation.
     */
    private Map<String, Operator> operators = new TreeMap<String, Operator>(String.CASE_INSENSITIVE_ORDER);

    /**
     * All defined functions with name and implementation.
     */
    private Map<String,Function> functions = new TreeMap<String, Function>(String.CASE_INSENSITIVE_ORDER);

    /**
     * All defined variables with name and value.
     */
    private Map<String, Double> variables = new TreeMap<String, Double>(String.CASE_INSENSITIVE_ORDER);

    private DoubleFunction prepared;

    /**
     * What character to use for decimal separators.
     */
    private static final char decimalSeparator = '.';

    /**
     * What character to use for minus sign (negative values).
     */
    private static final char minusSign = '-';
    
    private interface DoubleFunction {
        double apply();
    }
    
    private class OptimizingDoubleFunction implements DoubleFunction {
        
        private final DoubleFunction function;
        private final boolean dependsOnVar;

        public OptimizingDoubleFunction(DoubleFunction function, boolean dependsOnVar) {
            this.dependsOnVar = dependsOnVar;
            if (dependsOnVar) {
                this.function = function;
            } else {
                double result = function.apply();
                this.function = () -> result;
            }
        }
        
        public double apply() {
            return function.apply();
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof OptimizingDoubleFunction) {
                OptimizingDoubleFunction casted = (OptimizingDoubleFunction) obj;
                return casted.function == function;
            } else {
                DoubleFunction casted = (DoubleFunction) obj;
                return casted == function;
            }
        }
    }

    private static final DoubleFunction PARAMS_START = new DoubleFunction() {

        @Override
        public double apply() {
            return 0;
        }
    };

    /**
     * The expression evaluators exception class.
     */
    public static class ExpressionException extends RuntimeException {
        private static final long serialVersionUID = 1118142866870779047L;

        public ExpressionException(String message) {
            super(message);
        }
    }

    public abstract class Function {
        /**
         * Name of this function.
         */
        private String name;
        /**
         * Number of parameters expected for this function.
         * <code>-1</code> denotes a variable number of parameters.
         */
        private int numParams;

        /**
         * Creates a new function with given name and parameter count.
         *
         * @param name
         *            The name of the function.
         * @param numParams
         *            The number of parameters for this function.
         *            <code>-1</code> denotes a variable number of parameters.
         */
        public Function(String name, int numParams) {
            this.name = name.toUpperCase(Locale.ROOT);
            this.numParams = numParams;
        }

        public String getName() {
            return name;
        }

        public int getNumParams() {
            return numParams;
        }

        public boolean numParamsVaries() {
            return numParams < 0;
        }
        public abstract double eval(double[] lazyParams);
    }

    /**
     * Abstract definition of a supported operator. An operator is defined by
     * its name (pattern), precedence and if it is left- or right associative.
     */
    public abstract class Operator {
        /**
         * This operators name (pattern).
         */
        private String oper;
        /**
         * Operators precedence.
         */
        private int precedence;
        /**
         * Operator is left associative.
         */
        private boolean leftAssoc;

        /**
         * Creates a new operator.
         * 
         * @param oper
         *            The operator name (pattern).
         * @param precedence
         *            The operators precedence.
         * @param leftAssoc
         *            <code>true</code> if the operator is left associative,
         *            else <code>false</code>.
         */
        public Operator(String oper, int precedence, boolean leftAssoc) {
            this.oper = oper;
            this.precedence = precedence;
            this.leftAssoc = leftAssoc;
        }

        public String getOper() {
            return oper;
        }

        public int getPrecedence() {
            return precedence;
        }

        public boolean isLeftAssoc() {
            return leftAssoc;
        }

        /**
         * Implementation for this operator.
         * 
         * @param v1
         *            Operand 1.
         * @param v2
         *            Operand 2.
         * @return The result of the operation.
         */
        public abstract double eval(double v1, double v2);
    }

    /**
     * Expression tokenizer that allows to iterate over a {@link String}
     * expression token by token. Blank characters will be skipped.
     */
    private class Tokenizer implements Iterator<String> {

        /**
         * Actual position in expression string.
         */
        private int pos = 0;

        /**
         * The original input expression.
         */
        private String input;
        /**
         * The previous token or <code>null</code> if none.
         */
        private String previousToken;

        /**
         * Creates a new tokenizer for an expression.
         * 
         * @param input
         *            The expression string.
         */
        public Tokenizer(String input) {
            this.input = input.trim();
        }

        @Override
        public boolean hasNext() {
            return (pos < input.length());
        }

        /**
         * Peek at the next character, without advancing the iterator.
         * 
         * @return The next character or character 0, if at end of string.
         */
        private char peekNextChar() {
            if (pos < (input.length() - 1)) {
                return input.charAt(pos + 1);
            } else {
                return 0;
            }
        }

        @Override
        public String next() {
            StringBuilder token = new StringBuilder();
            if (pos >= input.length()) {
                return previousToken = null;
            }
            char ch = input.charAt(pos);
            while (Character.isWhitespace(ch) && pos < input.length()) {
                ch = input.charAt(++pos);
            }
            if (Character.isDigit(ch)) {
                while ((Character.isDigit(ch) || ch == decimalSeparator
                                                || ch == 'e' || ch == 'E'
                                                || (ch == minusSign && token.length() > 0 
                                                    && ('e'==token.charAt(token.length()-1) || 'E'==token.charAt(token.length()-1)))
                                                || (ch == '+' && token.length() > 0 
                                                    && ('e'==token.charAt(token.length()-1) || 'E'==token.charAt(token.length()-1)))
                                                ) && (pos < input.length())) {
                    token.append(input.charAt(pos++));
                    ch = pos == input.length() ? 0 : input.charAt(pos);
                }
            } else if (ch == minusSign
                    && Character.isDigit(peekNextChar())
                    && ("(".equals(previousToken) || ",".equals(previousToken)
                            || previousToken == null || operators
                                .containsKey(previousToken))) {
                token.append(minusSign);
                pos++;
                token.append(next());
            } else if (Character.isLetter(ch) || firstVarChars.indexOf(ch) >= 0) {
                while ((Character.isLetter(ch) || Character.isDigit(ch)
                        || varChars.indexOf(ch) >= 0 || token.length() == 0 && firstVarChars.indexOf(ch) >= 0)
                        && (pos < input.length())) {
                    token.append(input.charAt(pos++));
                    ch = pos == input.length() ? 0 : input.charAt(pos);
                }
            } else if (ch == '(' || ch == ')' || ch == ',') {
                token.append(ch);
                pos++;
            } else {
                while (!Character.isLetter(ch) && !Character.isDigit(ch)
                        && firstVarChars.indexOf(ch) < 0 && !Character.isWhitespace(ch)
                        && ch != '(' && ch != ')' && ch != ','
                        && (pos < input.length())) {
                    token.append(input.charAt(pos));
                    pos++;
                    ch = pos == input.length() ? 0 : input.charAt(pos);
                    if (ch == minusSign) {
                        break;
                    }
                }
                if (!operators.containsKey(token.toString())) {
                    throw new ExpressionException("Unknown operator '" + token
                            + "' at position " + (pos - token.length() + 1));
                }
            }
            return previousToken = token.toString();
        }

        @Override
        public void remove() {
            throw new ExpressionException("remove() not supported");
        }

        /**
         * Get the actual character position in the string.
         * 
         * @return The actual character position.
         */
        public int getPos() {
            return pos;
        }

    }

    /**
     * Creates a new expression instance from an expression string with a given
     * default match context.
     * 
     * @param expression
     *            The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
     *            <code>"sin(y)>0 & max(z, 3)>3"</code>
     * @param defaultMathContext
     *            The {@link MathContext} to use by default.
     */
    public DoubleBasedExpression(String expression) {
        this.expression = expression;
        addOperator(new Operator("+", 20, true) {
            @Override
            public double eval(double v1, double v2) {
                return v1 + v2;
            }
        });
        addOperator(new Operator("-", 20, true) {
            @Override
            public double eval(double v1, double v2) {
                return v1 - v2;
            }
        });
        addOperator(new Operator("*", 30, true) {
            @Override
            public double eval(double v1, double v2) {
                return v1 * v2;
            }
        });
        addOperator(new Operator("/", 30, true) {
            @Override
            public double eval(double v1, double v2) {
                return v1 / v2;
            }
        });
        addOperator(new Operator("%", 30, true) {
            @Override
            public double eval(double v1, double v2) {
                return v1 % v2;
            }
        });
        addOperator(new Operator("^", 40, false) {
            @Override
            public double eval(double v1, double v2) {
                return Math.pow(v1, v2);
            }
        });
        addOperator(new Operator("&&", 4, false) {
            @Override
            public double eval(double v1, double v2) {
                boolean b1 = v1 != 0;
                boolean b2 = v2 != 0;
                return b1 && b2 ? 1 : 0;
            }
        });

        addOperator(new Operator("||", 2, false) {
            @Override
            public double eval(double v1, double v2) {
                boolean b1 = v1 != 0;
                boolean b2 = v2 != 0;
                return b1 || b2 ? 1 : 0;
            }
        });

        addOperator(new Operator(">", 10, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 > v2 ? 1 : 0;
            }
        });

        addOperator(new Operator(">=", 10, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 >= v2 ? 1 : 0;
            }
        });

        addOperator(new Operator("<", 10, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 < v2 ? 1 : 0;
            }
        });

        addOperator(new Operator("<=", 10, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 <= v2 ? 1 : 0;
            }
        });

        addOperator(new Operator("=", 7, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 > v2 ? 1 : 0;
            }
        });
        addOperator(new Operator("==", 7, false) {
            @Override
            public double eval(double v1, double v2) {
                return operators.get("=").eval(v1, v2);
            }
        });

        addOperator(new Operator("!=", 7, false) {
            @Override
            public double eval(double v1, double v2) {
                return v1 != v2 ? 1 : 0;
            }
        });
        addOperator(new Operator("<>", 7, false) {
            @Override
            public double eval(double v1, double v2) {
                return operators.get("!=").eval(v1, v2);
            }
        });

        addFunction(new Function("NOT", 1) {
            @Override
            public double eval(double[] parameters) {
                boolean zero = parameters[0] == 0;
                return zero ? 1 : 0;
            }
        });

        addFunction(new Function("SIN", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.sin(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("COS", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.cos(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("TAN", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.tan(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("ASIN", 1) { // added by av
            @Override
            public double eval(double[] parameters) {
                return Math.asin(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("ACOS", 1) { // added by av
            @Override
            public double eval(double[] parameters) {
                return Math.acos(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("ATAN", 1) { // added by av
            @Override
            public double eval(double[] parameters) {
                return Math.atan(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("SINH", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.sinh(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("COSH", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.cosh(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("TANH", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.tanh(Math.toRadians(parameters[0]));
            }
        });
        addFunction(new Function("RAD", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.toRadians(parameters[0]);
            }
        });
        addFunction(new Function("DEG", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.toDegrees(parameters[0]);
            }
        });
        addFunction(new Function("MAX", -1) {
            @Override
            public double eval(double[] parameters) {
                if (parameters.length == 0) {
                    throw new ExpressionException("MAX requires at least one parameter");
                }
                double max = Double.MIN_VALUE;
                for (double parameter : parameters) {
                    if (parameter > max) {
                        max = parameter;
                    }
                }
                return max;
            }
        });
        addFunction(new Function("MIN", -1) {
            @Override
            public double eval(double[] parameters) {
                if (parameters.length == 0) {
                    throw new ExpressionException("MIN requires at least one parameter");
                }
                double min = Double.MAX_VALUE;
                for (double parameter : parameters) {
                    if (parameter < min) {
                        min = parameter;
                    }
                }
                return min;
            }
        });
        addFunction(new Function("CLAMP", 3) {
            @Override
            public double eval(double[] parameters) {
                if (parameters[0] < parameters[1]) return parameters[1];
                if (parameters[0] > parameters[2]) return parameters[2];
                return parameters[0];
            }
        });
        addFunction(new Function("ABS", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.abs(parameters[0]);
            }
        });
        addFunction(new Function("LOG", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.log(parameters[0]);
            }
        });
        addFunction(new Function("LOG10", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.log10(parameters[0]);
            }
        });
        addFunction(new Function("ROUND", 2) {
            @Override
            public double eval(double[] parameters) {
                return Math.round(parameters[0]) / Math.pow(10, parameters[1]);
            }
        });
        addFunction(new Function("FLOOR", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.floor(parameters[0]);
            }
        });
        addFunction(new Function("CEILING", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.ceil(parameters[0]);
            }
        });
        addFunction(new Function("SQRT", 1) {
            @Override
            public double eval(double[] parameters) {
                return Math.sqrt(parameters[0]);
            }
        });

        variables.put("e", e);
        variables.put("PI", PI);
        variables.put("TRUE", 1.0);
        variables.put("FALSE", 0.0);

    }

    /**
     * Is the string a number?
     * 
     * @param st
     *            The string.
     * @return <code>true</code>, if the input string is a number.
     */
    private boolean isNumber(String st) {
        if (st.charAt(0) == minusSign && st.length() == 1) return false;
        if (st.charAt(0) == '+' && st.length() == 1) return false;
        if (st.charAt(0) == 'e' ||  st.charAt(0) == 'E') return false;
        for (char ch : st.toCharArray()) {
            if (!Character.isDigit(ch) && ch != minusSign
                    && ch != decimalSeparator
                                        && ch != 'e' && ch != 'E' && ch != '+')
                return false;
        }
        return true;
    }

    /**
     * Implementation of the <i>Shunting Yard</i> algorithm to transform an
     * infix expression to a RPN expression.
     * 
     * @param expression
     *            The input expression in infx.
     * @return A RPN representation of the expression, with each token as a list
     *         member.
     */
    private List<String> shuntingYard(String expression) {
        List<String> outputQueue = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();

        Tokenizer tokenizer = new Tokenizer(expression);

        String lastFunction = null;
        String previousToken = null;
        while (tokenizer.hasNext()) {
            String token = tokenizer.next();
            if (isNumber(token)) {
                outputQueue.add(token);
            } else if (variables.containsKey(token)) {
                outputQueue.add(token);
            } else if (functions.containsKey(token.toUpperCase(Locale.ROOT))) {
                stack.push(token);
                lastFunction = token;
            } else if (Character.isLetter(token.charAt(0))) {
                stack.push(token);
            } else if (",".equals(token)) {
                if (operators.containsKey(previousToken)) {
                    throw new ExpressionException("Missing parameter(s) for operator " + previousToken +
                                                      " at character position " + (tokenizer.getPos() - 1 - previousToken.length()));
                }
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    outputQueue.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new ExpressionException("Parse error for function '"
                            + lastFunction + "'");
                }
            } else if (operators.containsKey(token)) {
                if (",".equals(previousToken) || "(".equals(previousToken)) {
                    throw new ExpressionException("Missing parameter(s) for operator " + token +
                                                      " at character position " + (tokenizer.getPos() - token.length()));
                }
                Operator o1 = operators.get(token);
                String token2 = stack.isEmpty() ? null : stack.peek();
                while (token2!=null &&
                        operators.containsKey(token2)
                        && ((o1.isLeftAssoc() && o1.getPrecedence() <= operators
                                .get(token2).getPrecedence()) || (o1
                                .getPrecedence() < operators.get(token2)
                                .getPrecedence()))) {
                    outputQueue.add(stack.pop());
                    token2 = stack.isEmpty() ? null : stack.peek();
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                if (previousToken != null) {
                    if (isNumber(previousToken)) {
                        throw new ExpressionException(
                                "Missing operator at character position "
                                        + tokenizer.getPos());
                    }
                    // if the ( is preceded by a valid function, then it
                    // denotes the start of a parameter list
                    if (functions.containsKey(previousToken.toUpperCase(Locale.ROOT))) {
                        outputQueue.add(token);
                    }
                }
                stack.push(token);
            } else if (")".equals(token)) {
                if (operators.containsKey(previousToken)) {
                    throw new ExpressionException("Missing parameter(s) for operator " + previousToken +
                                                      " at character position " + (tokenizer.getPos() - 1 - previousToken.length()));
                }
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    outputQueue.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new ExpressionException("Mismatched parentheses");
                }
                stack.pop();
                if (!stack.isEmpty()
                        && functions.containsKey(stack.peek().toUpperCase(
                                Locale.ROOT))) {
                    outputQueue.add(stack.pop());
                }
            }
            previousToken = token;
        }
        while (!stack.isEmpty()) {
            String element = stack.pop();
            if ("(".equals(element) || ")".equals(element)) {
                throw new ExpressionException("Mismatched parentheses");
            }
            if (!operators.containsKey(element)) {
                throw new ExpressionException("Unknown operator or function: "
                        + element);
            }
            outputQueue.add(element);
        }
        return outputQueue;
    }

    /**
     * Evaluates the expression.
     * 
     * @return The result of the expression.
     */
    public double eval() {
        DoubleFunction function = getPreparedExpression();
        return function.apply();
    }
    
    private DoubleFunction getPreparedExpression() {
        if (prepared == null) {
            prepared = prepare();
        }
        return prepared;
    }

    /**
     * Prepares the expression.
     * 
     * @return Performance oriented, prepared version of the expression
     */
    public DoubleFunction prepare() {

        Stack<OptimizingDoubleFunction> stack = new Stack<OptimizingDoubleFunction>();

        for (final String token : getRPN()) {
            if (operators.containsKey(token)) {
                final OptimizingDoubleFunction v1 = stack.pop();
                final OptimizingDoubleFunction v2 = stack.pop();
                DoubleFunction v1func = v1.function;
                DoubleFunction v2func = v2.function;
                Operator operator = operators.get(token);
                OptimizingDoubleFunction function = new OptimizingDoubleFunction(() -> operator.eval(v2func.apply(), v1func.apply()), v1.dependsOnVar || v2.dependsOnVar);
                stack.push(function);
            } else if (variables.containsKey(token)) {
                stack.push(new OptimizingDoubleFunction(() -> variables.get(token), true));
            } else if (functions.containsKey(token.toUpperCase(Locale.ROOT))) {
                Function f = functions.get(token.toUpperCase(Locale.ROOT));
                ArrayList<OptimizingDoubleFunction> p = new ArrayList<>(
                        !f.numParamsVaries() ? f.getNumParams() : 0);
                // pop parameters off the stack until we hit the start of 
                // this function's parameter list
                boolean dependsOnVar = false;
                while (!stack.isEmpty() && !stack.peek().equals(PARAMS_START)) {
                    OptimizingDoubleFunction param = stack.pop();
                    p.add(0, param);
                }
                if (stack.peek().equals(PARAMS_START)) {
                    stack.pop();
                }
                DoubleFunction[] functionArr = new DoubleFunction[p.size()];
                List<Integer> paramsToUpdate = new ArrayList<>();
                for (int i = 0; i < p.size(); i++) {
                    OptimizingDoubleFunction func = p.get(i);
                    if (func.dependsOnVar) {
                        dependsOnVar = true;
                        paramsToUpdate.add(i);
                    }
                    functionArr[i] = func.function;
                }
                int[] paramsToUpdateArr = new int[paramsToUpdate.size()];
                for (int i = 0; i < paramsToUpdate.size(); i++) {
                    paramsToUpdateArr[i] = paramsToUpdate.get(i);
                }
                
                double[] arr = new double[functionArr.length];
                for (int i = 0; i < functionArr.length; i++) {
                    arr[i] = functionArr[i].apply();
                }
                
                
                DoubleFunction function = new DoubleFunction() {

                    @Override
                    public double apply() {
                        updateParam(functionArr, paramsToUpdateArr, arr);
                        return f.eval(arr);
                    }

                    private void updateParam(DoubleFunction[] functionArr, int[] paramsToUpdateArr, double[] arr) {
                        for (int i : paramsToUpdateArr) {
                            arr[i] = functionArr[i].apply();
                        }
                    }
                };
                
                stack.push(new OptimizingDoubleFunction(function, dependsOnVar));
            } else if ("(".equals(token)) {
                stack.push(new OptimizingDoubleFunction(PARAMS_START, true));
            } else {
                double num = Double.parseDouble(token);
                stack.push(new OptimizingDoubleFunction(() -> num, false));
            }
        }
        return stack.pop().function;
    }

    /**
     * Sets the characters other than letters and digits that are valid as the
     * first character of a variable.
     *
     * @param chars
     *            The new set of variable characters.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression setFirstVariableCharacters(String chars) {
        this.firstVarChars = chars;
        return this;
    }

    /**
     * Sets the characters other than letters and digits that are valid as the
     * second and subsequent characters of a variable.
     *
     * @param chars
     *            The new set of variable characters.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression setVariableCharacters(String chars) {
        this.varChars = chars;
        return this;
    }

    /**
     * Adds an operator to the list of supported operators.
     * 
     * @param operator
     *            The operator to add.
     * @return The previous operator with that name, or <code>null</code> if
     *         there was none.
     */
    public Operator addOperator(Operator operator) {
        return operators.put(operator.getOper(), operator);
    }

    /**
     * Adds a function to the list of supported functions
     * 
     * @param function
     *            The function to add.
     * @return The previous operator with that name, or <code>null</code> if
     *         there was none.
     */
    public Function addFunction(Function function) {
        return (Function) functions.put(function.getName(), function);
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable name.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression setVariable(String variable, double value) {
        variables.put(variable, value);
        return this;
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable to set.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression setVariable(String variable, String value) {
        if (isNumber(value))
            variables.put(variable, Double.parseDouble(value));
        else {
            throw new ExpressionException("Only number variables supported in this expression version.");
        }
        return this;
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable to set.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression with(String variable, double value) {
        return setVariable(variable, value);
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable to set.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression and(String variable, String value) {
        return setVariable(variable, value);
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable to set.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression and(String variable, double value) {
        return setVariable(variable, value);
    }

    /**
     * Sets a variable value.
     * 
     * @param variable
     *            The variable to set.
     * @param value
     *            The variable value.
     * @return The expression, allows to chain methods.
     */
    public DoubleBasedExpression with(String variable, String value) {
        return setVariable(variable, value);
    }

    /**
     * Get an iterator for this expression, allows iterating over an expression
     * token by token.
     * 
     * @return A new iterator instance for this expression.
     */
    public Iterator<String> getExpressionTokenizer() {
        return new Tokenizer(this.expression);
    }

    /**
     * Cached access to the RPN notation of this expression, ensures only one
     * calculation of the RPN per expression instance. If no cached instance
     * exists, a new one will be created and put to the cache.
     * 
     * @return The cached RPN instance.
     */
    private List<String> getRPN() {
        List<String> rpn = shuntingYard(this.expression);
        validate(rpn);
        return rpn;
    }

    /**
     * Check that the expression has enough numbers and variables to fit the
     * requirements of the operators and functions, also check 
     * for only 1 result stored at the end of the evaluation.
     */
    private void validate(List<String> rpn) {
        /*-
        * Thanks to Norman Ramsey:
        * http://http://stackoverflow.com/questions/789847/postfix-notation-validation
        */
        // each push on to this stack is a new function scope, with the value of each
        // layer on the stack being the count of the number of parameters in that scope
        Stack<Integer> stack = new Stack<Integer>();

        // push the 'global' scope
        stack.push(0);

        for (final String token : rpn) {
            if (operators.containsKey(token)) {
                if (stack.peek() < 2) {
                    throw new ExpressionException("Missing parameter(s) for operator " + token);
                }
                // pop the operator's 2 parameters and add the result
                stack.set(stack.size() - 1, stack.peek() - 2 + 1);
            } else if (variables.containsKey(token)) {
                stack.set(stack.size() - 1, stack.peek() + 1);
            } else if (functions.containsKey(token.toUpperCase(Locale.ROOT))) {
                Function f = functions.get(token.toUpperCase(Locale.ROOT));
                int numParams = stack.pop();
                if (!f.numParamsVaries() && numParams != f.getNumParams()) {
                    throw new ExpressionException("Function " + token + " expected " + f.getNumParams() + " parameters, got " + numParams);
                }
                if (stack.size() <= 0) {
                    throw new ExpressionException("Too many function calls, maximum scope exceeded");
                }
                // push the result of the function
                stack.set(stack.size() - 1, stack.peek() + 1);
            } else if ("(".equals(token)) {
                stack.push(0);
            } else {
                stack.set(stack.size() - 1, stack.peek() + 1);
            }
        }

        if (stack.size() > 1) {
            throw new ExpressionException("Too many unhandled function parameter lists");
        } else if (stack.peek() > 1) {
            throw new ExpressionException("Too many numbers or variables");
        } else if (stack.peek() < 1) {
            throw new ExpressionException("Empty expression");
        }
    }

    /**
     * Get a string representation of the RPN (Reverse Polish Notation) for this
     * expression.
     * 
     * @return A string with the RPN representation for this expression.
     */
    public String toRPN() {
        StringBuilder result = new StringBuilder();
        for (String st : getRPN()) {
            if (result.length() != 0)
                result.append(" ");
            result.append(st);
        }
        return result.toString();
    }

    /**
     * Exposing declared variables in the expression.
     * @return All declared variables.
     */
    public Set<String> getDeclaredVariables() {
        return Collections.unmodifiableSet(variables.keySet());
    }

    /**
     * Exposing declared operators in the expression.
     * @return All declared operators.
     */
    public Set<String> getDeclaredOperators() {
        return Collections.unmodifiableSet(operators.keySet());
    }

    /**
     * Exposing declared functions.
     * @return All declared functions.
     */
    public Set<String> getDeclaredFunctions() {
        return Collections.unmodifiableSet(functions.keySet());
    }

    /**
     * @return The original expression string
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Returns a list of the variables in the expression.
     * 
     * @return A list of the variable names in this expression.
     */
    public List<String> getUsedVariables() {
        List<String> result = new ArrayList<String>();
        Tokenizer tokenizer = new Tokenizer(expression);
        while (tokenizer.hasNext()) {
            String token = tokenizer.next();
            if (functions.containsKey(token) || operators.containsKey(token)
                    || token.equals("(") || token.equals(")")
                    || token.equals(",") || isNumber(token)
                    || token.equals("PI") || token.equals("e")
                    || token.equals("TRUE") || token.equals("FALSE")) {
                continue;
            }
            result.add(token);
        }
        return result;
    }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DoubleBasedExpression that = (DoubleBasedExpression) o;
    if (this.expression == null) {
      return that.expression == null;
    } else {
      return this.expression.equals(that.expression);
    }
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.expression == null ? 0 : this.expression.hashCode();
  }
  
  
  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.expression;
  }

}