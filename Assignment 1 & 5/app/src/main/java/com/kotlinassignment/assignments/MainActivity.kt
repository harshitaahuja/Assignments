package com.kotlinassignment.assignments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kotlinassignment.assignments.dummy.DummyContent

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() , ItemFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@MainActivity)

        // Display a message on alert dialog
        builder.setMessage("Go to detail view")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(applicationContext,"Ok,you tapped "+ item.details,Toast.LENGTH_SHORT).show()

        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->
           // Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        showListButton.setOnClickListener{ view ->
            supportFragmentManager.inTransaction {
                add(R.id.frameLayoutContent, ItemFragment.Companion.newInstance(1))
            }
        }


    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
