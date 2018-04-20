package com.metacube.assignment3

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class FirstFragment : Fragment(), View.OnClickListener {
    private var buttonFtoParentA: Button? = null
    private var buttonFtoF: Button? = null
    private var mListener: IFragmentToActivityListener? = null
    private var rootView: View? = null
    private var editText1: EditText? = null
    private var editText2: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_first, container, false)
        initView(rootView)
        val strtext = arguments!!.getString("Message")
        val fragmentAText = rootView!!.findViewById(R.id.fragmentAText) as TextView
        fragmentAText.append(" " + strtext!!)
        Toast.makeText(context, strtext, Toast.LENGTH_SHORT).show()
        return rootView
    }

    fun initView(parentView: View?) {

        buttonFtoParentA = rootView!!.findViewById(R.id.buttonFtoA) as Button
        buttonFtoF = rootView!!.findViewById(R.id.buttonFtoF) as Button
        buttonFtoF = rootView!!.findViewById(R.id.buttonFtoF) as Button
        editText1 = rootView!!.findViewById(R.id.editText1) as EditText
        editText2 = rootView!!.findViewById(R.id.editText2) as EditText
        buttonFtoParentA!!.setOnClickListener(this)
        buttonFtoF!!.setOnClickListener(this)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IFragmentToActivityListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.buttonFtoA) {
            if (mListener != null) {
                if (editText1!!.text.toString().isEmpty()) {
                    editText1!!.error = "Please enter a no"
                    return
                }
                if (editText2!!.text.toString().isEmpty()) {
                    editText2!!.error = "Please enter a no"
                    return
                }
                val numberOne = Integer.parseInt(editText1!!.text.toString())
                val numberTwo = Integer.parseInt(editText2!!.text.toString())
                mListener!!.addNumbers(numberOne, numberTwo)

            }
        } else if (v.id == R.id.buttonFtoF) {
            if (mListener != null) {
                mListener!!.communicateToOtherFragment("Called From Fragment A")
            }
        }
    }

    interface IFragmentToActivityListener {
        fun addNumbers(firstNo: Int, secondNo: Int)
        fun communicateToOtherFragment(message: String)
    }


}



