package com.example.calculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View;
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("계산기")

        //버튼에 리스너 달아주기
        btn0.setOnClickListener(View.OnClickListener {
            txt1.append("0")
        })
        btn1.setOnClickListener(View.OnClickListener {
            txt1.append("1")
        })
        btn2.setOnClickListener(View.OnClickListener {
            txt1.append("2")
        })
        btn3.setOnClickListener(View.OnClickListener {
            txt1.append("3")
        })
        btn4.setOnClickListener(View.OnClickListener {
            txt1.append("4")
        })
        btn5.setOnClickListener(View.OnClickListener {
            txt1.append("5")
        })
        btn6.setOnClickListener(View.OnClickListener {
            txt1.append("6")
        })
        btn7.setOnClickListener(View.OnClickListener {
            txt1.append("7")
        })
        btn8.setOnClickListener(View.OnClickListener {
            txt1.append("8")
        })
        btn9.setOnClickListener(View.OnClickListener {
            txt1.append("9")
        })

        //연산기호에 리스너 달아주기
        AddBtn.setOnClickListener(View.OnClickListener {
            txt1.append(" + ")
        })
        SubBtn.setOnClickListener(View.OnClickListener {
            txt1.append(" - ")
        })
        MulBtn.setOnClickListener(View.OnClickListener {
            txt1.append(" × ")
        })
        DivBtn.setOnClickListener(View.OnClickListener {
            txt1.append(" / ")
        })
        ShareBtn.setOnClickListener(View.OnClickListener {
            txt1.append(" % ")
        })
        ClearBtn.setOnClickListener(View.OnClickListener {
            txt1.text = ""
            txt_result.text = ""
        })
        btnDot.setOnClickListener {
            txt1.append(".")
        }
        btnLeft.setOnClickListener {
            txt1.append(" ( ")
        }
        btnRigth.setOnClickListener {
            txt1.append(" ) ")
        }
        btn_back.setOnClickListener {
            var str : String = txt1.text.toString()
            if(!str.isNullOrEmpty()) {
                str = str.substring(0,str.length-1)
            }
            txt1.text = str
        }
        btnEqual.setOnClickListener(View.OnClickListener {
            var calResult : String
            var str : String = txt1.text.toString()

            if(!txt1.text.isBlank()){
                calResult = calPostfix(str)
                txt_result.text = "= "+(calResult)
            }
            else{
                Toast.makeText(this,"Invalid Value",Toast.LENGTH_SHORT).show();
            }
        })
    }

    private fun priority(op: String): Int {
        var prio: Int = 0
        when (op) {
            "×", "/", "%" -> prio = 2
            "-", "+" -> prio = 1
            "(", ")" -> prio = 0
        }
        return prio
    }
    private fun infixToPostfix(str: String): ArrayList<String> {
        val result = ArrayList<String>()
        val st = Stack<String>()
        var infix = str.split(" ")
        var postfix = ""

        for (i in 0..infix.size-1){
            when (infix[i]) {
                "(" -> {
                    st.push(infix[i])
                }
                ")" -> {
                    postfix = st.pop()
                    while (postfix != "(") {
                        result.add(postfix)
                        postfix = st.pop()
                    }
                }
                "+","-","/","%","×"-> {
                    while (!st.isEmpty() && priority(infix[i]) <= priority(st.peek())) {
                        postfix = st.pop()
                        result.add(postfix)
                    }
                    st.push(infix[i])
                }
                else -> {
                    result.add(infix[i])
                }
            }
        }
        while (!st.isEmpty()) {
            postfix = st.pop()
            result.add(postfix)
        }
        return result
    }
    private fun calPostfix(infix : String) : String{
        val st = Stack<String>()
        val postfix = infixToPostfix(infix)

        for(i in 0..postfix.size-1){
            var n1 : Double = 0.0
            var n2 : Double = 0.0
            when(postfix[i]){
                "+" ->{
                    n1 = st.pop().toDouble()
                    n2 = st.pop().toDouble()
                    st.push((n1+n2).toString())
                }
                "-" -> {
                    n1 = st.pop().toDouble()
                    n2 = st.pop().toDouble()
                    st.push((n1+-n2).toString())
                }
                "/" -> {
                    n1 = st.pop().toDouble()
                    n2 = st.pop().toDouble()
                    st.push((n1/n2).toString())
                }
                "%" ->{
                    n1 = st.pop().toDouble()
                    n2 = st.pop().toDouble()
                    st.push((n1%n2).toString())
                }
                "×" ->{
                    n1 = st.pop().toDouble()
                    n2 = st.pop().toDouble()
                    st.push((n1*n2).toString())
                }
                else -> {
                    st.push(postfix[i])
                }
            }
        }
        return st.pop()
    }
}
