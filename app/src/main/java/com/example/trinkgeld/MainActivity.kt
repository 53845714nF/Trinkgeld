package com.example.trinkgeld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private  const val START_PERCENT = 10

class MainActivity : AppCompatActivity() {
    private lateinit var baseInput: EditText
    private lateinit var percentInput: SeekBar
    private lateinit var percentLable: TextView
    private lateinit var tipOutput: TextView
    private lateinit var  totalOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseInput = findViewById(R.id.baseInput)
        percentInput = findViewById(R.id.percentInput)
        percentLable = findViewById(R.id.percentLable)
        tipOutput = findViewById(R.id.tipOutput)
        totalOutput = findViewById(R.id.totalOutput)

        percentInput.progress = START_PERCENT
        percentLable.text = "$START_PERCENT%"

        percentInput.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onPrograssCanged $progress")
                percentLable.text =  "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        baseInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged: $s")
                computeTipAndTotal()
            }

        })

    }

    private fun computeTipAndTotal() {
        // get value from base and tip
        if (baseInput.text.isEmpty()){
            tipOutput.text = ""
            totalOutput.text = ""
            return
        }
        val base = baseInput.text.toString().toDouble()
        val percent = percentInput.progress

        // Compute the tip and total
        val tip = base * percent / 100
        val total = base + tip

        // Update UI
        tipOutput.text = "%.2f €".format(tip)
        totalOutput.text = "%.2f €".format(total)

    }
}