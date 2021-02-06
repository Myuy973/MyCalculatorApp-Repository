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
            dotButton.setOnClickListener { setAndOutput(".")}
        }

        binding.apply {
            plusButton.setOnClickListener { setAndClear("+")}
            minusButton.setOnClickListener { setAndClear("-")}
            divisionButton.setOnClickListener { setAndClear("/")}
            multiplicationButton.setOnClickListener { setAndClear("*")}
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

        when (binding.numTextView.text.toString()) {
            "+", "-", "*", "/" ->
                binding.numTextView.text = ""
        }

        var beforetext = binding.numTextView.text.toString()
        if (beforetext == "0") beforetext = ""

        binding.numTextView.text = beforetext + numText
    }

    private fun setAndClear(mark: String) {
        var inputNumText = binding.numTextView.text.toString()
        list.add(inputNumText)
        list.add(mark)
        binding.numTextView.text = mark
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
        var lastNumText = binding.numTextView.text.toString()
        list.add(lastNumText)

        var num = 2
        var markType = 1
        var sumNum = list[0].toFloat()


        Log.d("value", "$list")
        Log.d("value", "${ListContentCheck(num, true)}")
        Log.d("value", "${ListContentCheck(markType)}")
        while (ListContentCheck(num, true) && ListContentCheck(markType)) {
            when(list[markType]) {
                "+" ->  sumNum += list[num].toFloat()
                "-" ->  sumNum -= list[num].toFloat()
                "*" ->  sumNum *= list[num].toFloat()
                "/" ->  sumNum /= list[num].toFloat()
            }

            markType += 2
            num += 2
        }

        binding.numTextView.text = sumNum.toString()

        list = mutableListOf()
        finishType = true
        Log.d("value", "${list}")
    }

    private fun ListContentCheck(num: Int, needNum: Boolean = false): Boolean {
        when(needNum) {
            true -> {
                try {
                    list[num].toFloat()
                } catch (e: NumberFormatException) {
                    return false
                } catch (e: ArrayIndexOutOfBoundsException) {
                    return false
                } catch (e: IndexOutOfBoundsException) {
                    return false
                }
                return true
            }
            false -> {
                try {
                    list[num]
                } catch (e: ArrayIndexOutOfBoundsException) {
                    return false
                } catch (e: IndexOutOfBoundsException) {
                    return false
                } catch (e: NumberFormatException) {
                    return false
                }
                return true
            }
        }
    }


}