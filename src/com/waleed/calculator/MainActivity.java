package com.waleed.calculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;

public class MainActivity extends Activity{
	
	private EditText numbers;
	private EditText history;
	private String lastCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        numbers = (EditText) findViewById(R.id.numberField);
        history = (EditText) findViewById(R.id.historyField); 
        
        registerForContextMenu(history);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
    {
    	System.out.println("IN HERE");
      if (v.getId()==R.id.historyField) 
      {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("title");
        String[] menuItems = {"one", "two"};
        for (int i = 0; i<menuItems.length; i++) {
          menu.add(Menu.NONE, i, i, menuItems[i]);
        }
      }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    
    public void onClick(View view)
    {
    	switch(view.getId())
    	{
    		case R.id.button0:
    			numbers.setText(numbers.getText() + "0");
    			break;
    		case R.id.button1:
    			numbers.setText(numbers.getText() + "1");
    			break;
    		case R.id.button2:
    			numbers.setText(numbers.getText() + "2");
    			break;
    		case R.id.button3:
    			numbers.setText(numbers.getText() + "3");
    			break;
    		case R.id.button4:
    			numbers.setText(numbers.getText() + "4");
    			break;
    		case R.id.button5:
    			numbers.setText(numbers.getText() + "5");
    			break;
    		case R.id.button6:
    			numbers.setText(numbers.getText() + "6");
    			break;
    		case R.id.button7:
    			numbers.setText(numbers.getText() + "7");
    			break;
    		case R.id.button8:
    			numbers.setText(numbers.getText() + "8");
    			break;
    		case R.id.button9:
    			numbers.setText(numbers.getText() + "9");
    			break;
    		case R.id.buttonPoint:
    			if(!checkDec(""+numbers.getText()))
    			{
    				numbers.setText(numbers.getText() + ".");
    			}
    			break;
    			
    		case R.id.buttonDel:
    			if(numbers.getText().length()>0)
    			{
    				numbers.setText(numbers.getText().subSequence(0, numbers.getText().length()-1));
    			}
    			System.out.println("DEL");
    			break;
    		case R.id.buttonClear:
    			numbers.setText("");
    			break;
    			
    		case R.id.buttonPlus:
    			if(numbers.length()>0 && !checkChar(numbers.getText().charAt(numbers.length()-1)))
    			{
    				numbers.setText(numbers.getText() + "+");
    			}
    			break;
    		case R.id.buttonMinus:
    			if(numbers.length()>0 && !checkChar(numbers.getText().charAt(numbers.length()-1)))
    			{
    				numbers.setText(numbers.getText() + "-");
    			}
    			break;
    		case R.id.buttonMul:
    			if(numbers.length()>0 && !checkChar(numbers.getText().charAt(numbers.length()-1)))
    			{
    				numbers.setText(numbers.getText() + "*");
    			}
    			break;
    		case R.id.buttonDiv:
    			if(numbers.length()>0 && !checkChar(numbers.getText().charAt(numbers.length()-1)))
    			{
    				numbers.setText(numbers.getText() + "/");
    			}
    			break;
    			
    		case R.id.buttonEq:
    			if(numbers.length()>0)
    			{
    				calculate(""+numbers.getText());
    			}
    	}
    }
    
    public boolean checkDec(String seq)
    {
    	boolean check = false;
    	
    	String temp = "";
    	//System.out.println(seq.length());
    	if(!seq.equals(""))
    	{
    		//System.out.println("TEMP: "+temp);
	    	int pos = seq.length()-1;
	    	//while(pos > -1 && !checkChar(seq.charAt(pos)))
	    	while(pos > -1)
	    	{
	    		temp = temp + seq.charAt(pos);
	    		//temp.concat(""+seq.charAt(pos));
	    		if(checkChar(seq.charAt(pos)))
	    		{
	    			break;
	    		}
	    		else
	    		{
	    			pos = pos - 1;
	    		}
	    	}
	    	
	    	//System.out.println("TEMP: "+temp);
	    	
	    	if(temp.contains("."))
	    	{
	    		check = true;
	    	}
    	}
    	
    	return check;
    }
    
    //if character is equal to {+,-,/,*,.} return true
    public boolean checkChar(char pred)
    {
    	boolean result = false;
    	
    	if(pred == '+' || pred == '-' || pred == '*' || pred == '/' || pred == '.')
    	{
    		result = true;
    	}
    	
    	return result;
    }
    
    public boolean checkOp(char pred)
    {
    	boolean result = false;
    	
    	if(pred == '+' || pred == '-' || pred == '*' || pred == '/')
    	{
    		result = true;
    	}
    	
    	return result;
    }
    
    public void calculate(String expression)
    {
    	System.out.println("INPUT: "+expression);
    	
    	int length = expression.length();
    	Stack<String> stack = new Stack<String>();
    	Queue<String> queue = new LinkedList<String>();
    	
    	while(length > 0 && checkChar(expression.charAt(length-1)))
    	{
    		expression = expression.substring(0, length-1);
    		length = length-1;
    	}
    	
    	/*if(checkChar(expression.charAt(length-1)))
    	{
    		expression = expression.substring(0, length-1);
    		length = length-1;
    	}*/
    	
    	lastCalculation = expression;
    	
		char temp;
		String number = "";
		
		//SCENARIO: first number is negative. DEAL.
		if(expression.length()!=0 && expression.charAt(0)=='-')
		{
			temp = expression.charAt(0);
			number = number + temp;
			expression = expression.substring(1, length);
			length = length - 1;
			temp = expression.charAt(0);
			
			while(!checkOp(temp) && length>0)
			{
				number = number + temp;
				expression = expression.substring(1, length);
				length = length - 1;
				if(length>0)
				{
					temp = expression.charAt(0);
				}
			}
		
			queue.add(number);
		}
		
		while(length>0)
		{
			temp = expression.charAt(0);
			
			if(checkOp(temp))
			{
				expression = expression.substring(1, length);
				length = length - 1;
				
				if(stack.isEmpty())
				{
					stack.push(""+temp);
				}
				else
				{
    				if(temp=='+' || temp =='-')
    				{
    					while(!stack.isEmpty())
    					{
    						queue.add(stack.pop());
    					}
    					stack.push(""+temp);
    				}
    				else if(temp=='*' || temp=='/')
    					{
    						while(stack.peek().equals(""+'*') || stack.peek().equals(""+'/'))
    						{
    							queue.add(stack.pop());
    							if(stack.isEmpty())
    							{
    								break;
    							}
    						}
    						stack.push(""+temp);
    					}
				}
			}
			else
			{
				number = "";
				while(!checkOp(temp) && length>0)
				{
					if(length==1)
					{
						number = number + temp;
						expression = expression.substring(0, 1);
						length = length - 1;
						//temp = expression.charAt(0);
					}
					else
					{
						//System.out.println("TEMP: "+temp);
						number = number + temp;
						expression = expression.substring(1, length);
						length = length - 1;
						temp = expression.charAt(0);
					}
				}
				queue.add(number);
			}
			System.out.println("QUEUE: "+queue.toString());
		}
		
		while(!stack.isEmpty())
		{
			queue.add(stack.pop());
		}
		
		System.out.println(queue.toString());
		
		Stack<Double> evaluation = new Stack<Double>();
		Double result = 0.0;
		while(!queue.isEmpty())
		{
			//CONDITIONS
			//Top of stack is operator
			//Length is greater than 1 to take into account negative numbers
			if(checkOp(queue.peek().charAt(0)) && queue.peek().length()==1)
			{
				String operator = queue.poll();
				double opA = evaluation.pop();
				double opB = evaluation.pop();
				switch(operator.charAt(0))
				{
					case '+':
						result = opB + opA;
						evaluation.push(result);
						break;
					case '-':
						result = opB - opA;
						evaluation.push(result);
						break;
					case '*':
						result = opB * opA;
						evaluation.push(result);
						break;
					case '/':
						result = opB / opA;
						evaluation.push(result);
						break;
				}
			}
			else
			{
				evaluation.push(Double.parseDouble(queue.poll()));
				System.out.println("FIN STACK: "+evaluation.toString());
			}
		}
		
		if(!evaluation.isEmpty())
		{
			System.out.println(evaluation.peek());
			if(Double.isInfinite(evaluation.peek()))
			{
				numbers.setText("");
				String existingText = ""+history.getText();
				if(history.getText().length()==0)
		    	{
		    		history.setText(lastCalculation + " = "+ evaluation.pop() + "\n");
		    	}
		    	else
		    	{
		    		history.setText(existingText + "\n" + lastCalculation + " = "+ evaluation.pop() + "\n");
		        	history.setSelection(history.getText().length());
		    	}
			}
			else
			{
		    	numbers.setText(""+evaluation.pop());
		    	String newAnswer = ""+numbers.getText();
		    	String existingText = ""+history.getText();
		    	if(history.getText().length()==0)
		    	{
		    		history.setText(lastCalculation + " = "+ newAnswer + "\n");
		    	}
		    	else
		    	{
		    		history.setText(existingText + "\n" + lastCalculation + " = "+ newAnswer + "\n");
		        	history.setSelection(history.getText().length());
		    	}
			}
		}
    }	  
}