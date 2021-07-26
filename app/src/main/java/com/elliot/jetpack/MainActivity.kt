package com.elliot.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG: String = "로그"

    }

    lateinit var myNumberViewModel: MyNumberViewModel

    private val numberTextView by lazy { findViewById<TextView>(R.id.number_textView) }
    private val userInputEditText by lazy { findViewById<EditText>(R.id.userinput_edittext) }
    private val plusBtn by lazy { findViewById<Button>(R.id.plus_btn)}
    private val minusBtn by lazy { findViewById<Button>(R.id.minus_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰모델 프로바이더를 통해 뷰모델 가져오기
        //라이프사이클을 가지고 있는 녀석을 넣어줌 즉 자기자신
        //우리가 가져오고 싶은 뷰모델 클래스를 넣어서 뷰모델 가져오기
        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)

        //뷰모델이 가지고 있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다.
        myNumberViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "MainActivity - myNumberViewModel - currentValue 라이브 데이터 값 변경 : $it")
            numberTextView.text = it.toString()

        })

        //리스너 연결
        plusBtn.setOnClickListener(this)
        minusBtn.setOnClickListener(this)
    }

    //클릭
    override fun onClick(view: View?) {
        val userInput = userInputEditText.text.toString().toInt()

        //뷰모델에 라이브데이터 값을 변경하는 메소드 실행
        when(view?.id){
            R.id.plus_btn ->
                myNumberViewModel.updateValue(actionType = ActionType.PLUS, userInput)
            R.id.minus_btn ->
                myNumberViewModel.updateValue(actionType = ActionType.MINUS, userInput)
        }
    }
}