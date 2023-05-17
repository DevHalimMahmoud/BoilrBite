package com.abdelhalim.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.abdelhalim.boilrbite.BoilrBite
import com.abdelhalim.boilrbite.OnItemClickListener
import com.abdelhalim.demo.databinding.ItemBinding
import com.abdelhalim.demo.databinding.ActivityMainBinding


lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val adapter = BoilrBite.createBoilrBiteAdapter(
            items = mutableListOf<DummyModel>(),
            layoutResId = R.layout.item,
            clickableViewIds = setOf(R.id.btn_dummy, R.id.tv_name, R.id.tv_university),
            compareItems = { old, new -> old == new },
            compareContents = { old, new -> old.id == new.id },
            block = { view, item ->
                val binding = DataBindingUtil.bind<ItemBinding>(view)
                binding?.tvName?.text = item.name
                binding?.tvUniversity?.text = item.university
            })
        binding.rvMain.adapter = adapter

        val dummyModels = listOf(
            DummyModel(1, "Abdelhalim", "Ain Shams University"),
            DummyModel(2, "Hassan", "Ain Shams University"),
            DummyModel(3, "Ahmed", "Ain Shams University"),
            DummyModel(4, "Mahmoud", "Ain Shams University"),
            DummyModel(5, "Ali", "Ain Shams University"),
            DummyModel(6, "John", "Ain Shams University"),
            DummyModel(7, "Alex", "Ain Shams University"),
            DummyModel(8, "Karem", "Ain Shams University"),
            DummyModel(9, "Mohamed", "Ain Shams University"),
            DummyModel(10, "Ali", "Ain Shams University"),
            DummyModel(11, "Omar", "Ain Shams University"),
            DummyModel(12, "Ola", "Ain Shams University"),
            DummyModel(13, "Sagda", "Ain Shams University"),
            DummyModel(14, "Alaa", "Ain Shams University"),
            DummyModel(15, "Kamal", "Ain Shams University"),
            DummyModel(16, "Abanob", "Ain Shams University"),
            DummyModel(17, "Moris", "Ain Shams University"),
            DummyModel(18, "Misho", "Ain Shams University"),
            DummyModel(20, "Nebula", "Ain Shams University"),
            DummyModel(19, "Samar", "Ain Shams University"),
            DummyModel(20, "Nebula", "Ain Shams University")
        )
        val start = System.currentTimeMillis()
        adapter.addOrUpdateItems(dummyModels)
        val end = System.currentTimeMillis()

        Log.d("TAG", "onCreate: ${end - start} ")
        adapter.onItemClickListener = object : OnItemClickListener<DummyModel, View?>() {
            override fun onItemClicked(view: View?, item: DummyModel, position: Int) {
                super.onItemClicked(view, item, position)
                when (view?.id) {
                    R.id.rv_item -> {
                        Toast.makeText(
                            this@MainActivity,
                            "item container click at position: $position",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    R.id.btn_dummy -> {
                        Toast.makeText(
                            this@MainActivity,
                            "button click at position: $position",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    R.id.tv_name -> {
                        Toast.makeText(
                            this@MainActivity,
                            "name click at position: $position",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    R.id.tv_university -> {
                        Toast.makeText(
                            this@MainActivity,
                            "university click at position: $position",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
        adapter.onSelectedItemChange = object : BoilrBite.OnSelectedItemChange<DummyModel> {
            override fun onSelectedItemChange(
                oldPosition: Int,
                oldItem: DummyModel?,
                newPosition: Int?,
                newItem: DummyModel?
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "new: " + newItem?.id.toString() + "  old: " + oldItem?.id.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}