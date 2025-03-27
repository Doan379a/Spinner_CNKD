package com.androtech.spinnertest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.androtech.spinnertest.custom.spinner.model.SpinWheelItemSectionModel
import com.androtech.spinnertest.databinding.ActivityMainBinding
import kotlin.random.Random

/**
 * Created by mohammed shahbaz on 17/04/24.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding:  ActivityMainBinding
    private var data: ArrayList<SpinWheelItemSectionModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMyTitleColor()
        setSpinner()
        binding.addButton.setOnClickListener {
            val newItemText = binding.editText.text.toString().trim()
            if (newItemText.isNotEmpty()) {
                val newItem = SpinWheelItemSectionModel().apply {
                    secondaryText = newItemText
                    color = Color.parseColor(generateRandomColor())
                }
                data.add(newItem)
                binding.luckySpinnerView.setData(data) // Cập nhật dữ liệu vòng quay
                binding.editText.text.clear() // Xóa nội dung trong EditText
                Log.d("DOAN_DO", "Data size: ${data.size}")
            } else {
                Toast.makeText(this, "Vui lòng nhập tên mới", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setMyTitleColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

    private fun setSpinner() {
        val colors = listOf("#8540bc", "#f69b2f")
        val colorCount = colors.size
        for (i in 1..12) {
            val spinWheelItemSectionModel = SpinWheelItemSectionModel().apply {
                topText = i.toString()
                secondaryText = if (i % 2 == 0) "Yes" else "No"
                color = Color.parseColor(colors[(i - 1) % colorCount])
            }
            data.add(spinWheelItemSectionModel)
        }
        binding.luckySpinnerView.setData(data)
        binding.luckySpinnerView.setBorderColor(R.color.color_b958e0)
        binding.luckySpinnerView.setRound(5)

        binding.play.setOnClickListener {
            val index: Int = getRandomIndex()
            binding.luckySpinnerView.startLuckyWheelWithTargetIndex(index)
        }

        binding.luckySpinnerView.setLuckyRoundItemSelectedListener { index ->
            Toast.makeText(applicationContext, "You got index ${data[index].topText}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getRandomIndex(): Int {
        return Random.nextInt(data.size)
    }
    private fun generateRandomColor(): String {
        val random = Random
        return String.format("#%02x%02x%02x", random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}