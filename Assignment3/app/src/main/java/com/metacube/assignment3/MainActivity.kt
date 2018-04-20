package com.metacube.assignment3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FirstFragment.IFragmentToActivityListener {
    override fun addNumbers(firstNo: Int, secondNo: Int) {
        Toast.makeText(this,"The sum is "+(firstNo + secondNo),Toast.LENGTH_LONG).show();
    }

    override fun communicateToOtherFragment(message: String) {
        try {
            val bundle = Bundle()
            bundle.putString("Message", message)
            val secondFragment = SecondFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, secondFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            secondFragment.arguments = bundle
        } catch (e: Exception) {
            Log.v("Error", e.localizedMessage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAtoF.setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putString("Message", "Called from Main Activity")
                val newFragment = FirstFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container, newFragment)
                transaction.commit()
                newFragment.arguments = bundle
                buttonAtoF.isActivated = false
                buttonAtoF.visibility = View.INVISIBLE
            } catch (e: Exception) {
                Log.v("Error", e.localizedMessage)
            }

        };
    }
}
