package com.example.flashcards

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener


class MainActivity : AppCompatActivity() {
    private var al: ArrayList<String?>? = null
    private var arrayAdapter: ArrayAdapter<String?>? = null
    private var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        al = ArrayList()
        al!!.add("php")
        al!!.add("c")
        al!!.add("python")
        al!!.add("java")
        al!!.add("html")
        al!!.add("c++")
        al!!.add("css")
        al!!.add("javascript")

        arrayAdapter = ArrayAdapter(this, R.layout.item, R.id.helloText, al!!)
        val flingContainer = findViewById<SwipeFlingAdapterView>(R.id.frame);

        flingContainer!!.adapter = arrayAdapter
        flingContainer!!.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                al!!.removeAt(0)
                arrayAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(this@MainActivity, "Left!");
            }

            override fun onRightCardExit(dataObject: Any) {
                makeToast(this@MainActivity, "Right!");
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                al!!.add("XML $i")
                arrayAdapter?.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++
            }

            override fun onScroll(scrollProgressPercent: Float) {
                val view: View = flingContainer!!.selectedView
                view.findViewById<View>(R.id.item_swipe_right_indicator)
                    .setAlpha(if (scrollProgressPercent < 0) -scrollProgressPercent else 0f)
                view.findViewById<View>(R.id.item_swipe_left_indicator)
                    .setAlpha(if (scrollProgressPercent > 0) scrollProgressPercent else 0f)
            }
        })


        // Optionally add an OnItemClickListener
        flingContainer!!.setOnItemClickListener { itemPosition, dataObject ->
            makeToast(
                this@MainActivity,
                "Clicked!"
            )
        }
    }

    fun makeToast(ctx: Context?, s: String?) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
    }


/*
    @OnClick(R.id.right)
    fun right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer!!.topCardListener.selectRight()
    }

    @OnClick(R.id.left)
    fun left() {
        flingContainer!!.topCardListener.selectLeft()
    }
*/
}