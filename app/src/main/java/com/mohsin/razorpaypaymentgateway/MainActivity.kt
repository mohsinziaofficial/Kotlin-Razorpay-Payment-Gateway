package com.mohsin.razorpaypaymentgateway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mohsin.razorpaypaymentgateway.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.payBtn.setOnClickListener {
            val amount = binding.amount.text.toString().trim()
            if (amount.isEmpty()) return@setOnClickListener

            processPayment(amount.toInt())
        }
    }

    private fun processPayment(amount : Int) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_QguTFxNUXLIXmx")

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Integration")
            options.put("description", "Learning tutorial")

            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")

            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            /** Generated from backend **/
            //      options.put("order_id", "order_DBJOWzybf0sJbb");

            /** Pass in paise in INR  ( Example  Rs 5 = 500 paise ) **/
            options.put("amount", "${(amount.toInt() * 100)}")//pass amount in currency subunits


            options.put("prefill.email", "random@gmail.com")
            options.put("prefill.contact", "+919442009211")

            checkout.open(this, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful: $p0", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed: $p1", Toast.LENGTH_SHORT).show()
    }
}