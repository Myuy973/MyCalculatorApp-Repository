package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding
import java.util.logging.SimpleFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list: MutableList<String> = mutableListOf()
    private var finishType: Boolean = false
    private var middleTotalType: Boolean = false

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

    // 数値を押された時の処理
    private fun setAndOutput(numText: String) {
        if (finishType) {
            binding.numTextView.text = ""
            formulaCreate()
            finishType = false
        }

        // 計算結果が表示されている場合、表示をリセット
        if (middleTotalType) {
            binding.numTextView.text = ""
            middleTotalType = false
        }


        when (binding.numTextView.text.toString()) {
            "+", "-", "*", "/" ->
                binding.numTextView.text = ""
        }

        var beforetext = binding.numTextView.text.toString()
        if (beforetext == "0") beforetext = ""

        binding.numTextView.text = beforetext + numText
    }

    // 演算子を押された時の処理
    private fun setAndClear(mark: String) {
        if (finishType) {
            binding.numTextView.text = "0"
            formulaCreate()
            finishType = false
            return
        }

        if (middleTotalType) {
            binding.numTextView.text = ""
            middleTotalType = false
            return
        }

        var inputNumText = binding.numTextView.text.toString()
        // 入力された文字が数値かどうか
        if (inputNumText.toFloatOrNull() != null ){
            list.add(inputNumText)
        }

        // 奇数番目であるかどうか
        if (list.size % 2 == 1) {
            list.add(mark)
            binding.numTextView.text = middleTotal()
        }

        formulaCreate()
        Log.d("value", "setAndClear: $list")
    }

    // テキストのみ初期化
    private fun clearTextView() {
        binding.numTextView.text = "0"
        Log.d("value", "${list}")
    }

    // テキストとリストを初期化
    private fun clearTextAndList() {
        binding.numTextView.text = "0"
        list = mutableListOf()
        formulaCreate()
        Log.d("value", "${list}")
    }

    // 最後の文字を削除
    private fun backTextView() {
        var backnum = binding.numTextView.text.toString().dropLast(1)
        if (backnum == "") backnum = "0"
        if (middleTotalType) backnum = ""
        binding.numTextView.text = backnum
    }

    private fun output() {
        // trueは演算子を押された後、つまり数値がListに追加された後なため
        // クリアしても大丈夫
        // 数値を押した後、[5, +] numtext: 10 middletotaltype: false
        // 演算子を押した後、[5, +, 10] numtext: 15 middletotaltype: true
        if (middleTotalType) {
            binding.numTextView.text = ""
            middleTotalType = false
        }
        // 最後の文字列を入力
        var lastNumText = binding.numTextView.text.toString()
        // 連続して文字列(演算子)を入力した場合、最後の文字は0にする　（[+,+], [+, .]）
        if (lastNumText.toFloatOrNull() == null && !ListContentCheck(list.size -1, true)) {
            list.add("0")
        } else {
            list.add(lastNumText)
        }

        // 式を生成
        formulaCreate()

        binding.numTextView.text = middleTotal()

        finishSetUp()
        Log.d("value", "${list}")
    }

    // 計算式出力
    private fun formulaCreate() {
        Log.d("value", "$list")
        var formula = ""
        for (s in list) {
            formula += ("$s ")
        }
//        if () Log.d("value", "OK!!!!!!")
        binding.formulaOutputText.text = formula
    }

    // 計算
    private fun middleTotal(): String {
        var num = 2
        var markType = 1
        var sumNum = 0f
        var sumNumString = ""
        if(ListContentCheck(0, true)) sumNum = list[0].toFloat()

        Log.d("value", "output: $list")
        Log.d("value", "output: ${ListContentCheck(num, true)}")
        Log.d("value", "output: ${ListContentCheck(markType)}")
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

        middleTotalType = true
        // 小数点含むならそのまま文字列に
        sumNumString = sumNum.toString()
        // 小数点以下がないのなら、Int()にしてから文字列に
        if ( sumNum - sumNum.toInt() == 0.0f) {
            sumNumString = sumNum.toInt().toString()
        }
        if ( sumNumString == "Infinity") {
            sumNumString = "Error"
            finishSetUp()
        }

        return sumNumString
    }

    // 計算後の処理（ListとfinishTypeの初期化）
    private fun finishSetUp() {
        list.clear()
        finishType = true
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