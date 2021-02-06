package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list: MutableList<String> = mutableListOf()
    private var finishType: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            zeroButton.setOnClickListener { setAndOutput("0")}
            oneButton.setOnClickListener { setAndOutput("1")}
            twoButton.setOnClickListener { setAndOutput("2")}
            threeButton.setOnClickListener { setAndOutput("3")}
            fourButton.setOnClickListener { setAndOutput("4")}
            fiveButton.setOnClickListener { setAndOutput("5")}
            sixButton.setOnClickListener { setAndOutput("6")}
            sevenButton.setOnClickListener { setAndOutput("7")}
            eightButton.setOnClickListener { setAndOutput("8")}
            nineButton.setOnClickListener { setAndOutput("9")}
        }

        binding.apply {
            plusButton.setOnClickListener { setAndClear("+")}
            minusButton.setOnClickListener { setAndClear("-")}
            divisionButton.setOnClickListener { setAndClear("*")}
            multiplicationButton.setOnClickListener { setAndClear("/")}
        }

        binding.apply {
            clearButton.setOnClickListener { clearTextView()}
            clearEntryButton.setOnClickListener { clearTextAndList()}
            backButton.setOnClickListener { backTextView()}
        }

        binding.equalButton.setOnClickListener { output() }

    }

    private fun setAndOutput(numText: String) {
        if (finishType == true) {
            binding.numTextView.text = ""
            finishType = false
        }

        var beforetext = binding.numTextView.text.toString()
        if (beforetext == "0") beforetext = ""
        binding.numTextView.text = beforetext + numText
    }

    private fun setAndClear(mark: String) {
        var inputNumText = binding.numTextView.text.toString()
        list.add(inputNumText)
        list.add(mark)
        binding.numTextView.text = ""
    }

    private fun clearTextView() {
        binding.numTextView.text = "0"
        Log.d("value", "${list}")
    }

    private fun clearTextAndList() {
        binding.numTextView.text = "0"
        list = mutableListOf()
        Log.d("value", "${list}")
    }

    private fun backTextView() {
        binding.numTextView.text = binding.numTextView.text.toString().dropLast(1)
    }

    private fun output() {
        var num = 2
        var markType = 1
        var sumNum = list[0].toInt()

        var lastNumText = binding.numTextView.text.toString()
        list.add(lastNumText)

        while (ListSizeCheck(num)) {
            when(list[markType]) {
                "+" ->  sumNum += list[num].toInt()
                "-" ->  sumNum -= list[num].toInt()
                "*" ->  sumNum *= list[num].toInt()
                "/" ->  sumNum /= list[num].toInt()
            }

            markType += 2
            num += 2
        }

        binding.numTextView.text = sumNum.toString()
        list = mutableListOf()
        finishType = true
        Log.d("value", "${list}")
    }

    private fun ListSizeCheck(num: Int): Boolean {
        try {
            list[num]
        } catch (e: ArrayIndexOutOfBoundsException) {
            return false
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
        return true
    }


}