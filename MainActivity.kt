package com.example.razorpaymentgateway

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var txtPaymentStatus: TextView
    private lateinit var editAmount: EditText
    private lateinit var btnPayNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtPaymentStatus = findViewById(R.id.paymentStatus)
        editAmount = findViewById(R.id.edit_amount)
        btnPayNow = findViewById(R.id.btn_pay)

        btnPayNow.setOnClickListener {
//           savePayments(editAmount.text.toString().trim().toInt())

            val amount = editAmount.text.toString().trim()

            if (amount.isNullOrEmpty()) {
                editAmount.error = "Amount Required"
                return@setOnClickListener
            } else {
//                savePayments(amount.toInt())
                savePayments(editAmount.text.toString().trim().toInt())
                Toast.makeText(this,"Validation Completed",Toast.LENGTH_SHORT).show()
            }

        }

        Checkout.preload(this@MainActivity)

    }

    private fun savePayments(amount : Int){
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_ya9y8QFj4JT57a")

        try {

            val options = JSONObject()

            options.put("name", "Mega Export")

            options.put("description", "Best payment")

            options.put("theme.color", "#3399cc")

            options.put("currency", "USD")

            options.put("amount", amount * 100)



            val retryObj = JSONObject()

            retryObj.put("enabled", true)

            retryObj.put("max_count", 4)

            options.put("retry", retryObj)



            checkout.open(this@MainActivity, options)

        } catch (e: Exception) {

            Toast.makeText(this@MainActivity, "Error in Payment : " + e.message, Toast.LENGTH_LONG)

                .show()

            e.printStackTrace()

        }

    }

    override fun onPaymentSuccess(p0: String?) {
//        TODO("Not yet implemented")
        txtPaymentStatus.text = p0
        txtPaymentStatus.setTextColor(Color.GREEN)

    }

    override fun onPaymentError(p0: Int, p1: String?) {
//        TODO("Not yet implemented")
        txtPaymentStatus.text = p1
        txtPaymentStatus.setTextColor(Color.RED)
    }
}