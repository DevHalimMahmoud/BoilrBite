package com.abdelhalim.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abdelhalim.boilrbite.BoilrBite
import com.abdelhalim.boilrbite.OnItemClickListener
import com.abdelhalim.demo.databinding.ActivityMainBinding
import com.abdelhalim.demo.databinding.Item1Binding
import com.abdelhalim.demo.databinding.Item2Binding


lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val adapter = BoilrBite.createBoilrBiteAdapter(
            items = mutableListOf<DummyModel>(),
            layoutResIds = setOf(R.layout.item1, R.layout.item2),
            clickableViewIds = setOf(R.id.btn_dummy, R.id.tv_name, R.id.tv_university),
            compareContents = { old, new -> old.id == new.id },
            bind = { view, item, viewType ->

                when (viewType) {
                    R.layout.item1 -> {
                        val binding = DataBindingUtil.bind<Item1Binding>(view)
                        binding?.tvName?.text = item.name
                        binding?.tvUniversity?.text = item.university
                    }

                    R.layout.item2 -> {
                        val binding = DataBindingUtil.bind<Item2Binding>(view)
                        binding?.tvName2?.text = item.name
                    }

                    else -> {
                        Toast.makeText(this, "ViewType not found", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onViewRecycled = { view, item, viewType ->
                when (viewType) {
                    R.layout.item1 -> {
                        DataBindingUtil.getBinding<Item1Binding>(view)?.unbind()
                    }

                    R.layout.item2 -> {
                        DataBindingUtil.getBinding<Item2Binding>(view)?.unbind()
                    }

                    else -> {
                        Toast.makeText(this, "ViewType not found", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            setViewType = { position, item ->
                when (position % 2) {
                    0 -> R.layout.item1
                    else -> R.layout.item2
                }
            }
        )
        binding.rvMain.adapter = adapter

        val dummyModels = listOf(
            DummyModel(1, "Abdelhalim", "University of Toronto"),
            DummyModel(2, "Hassan", "Harvard University"),
            DummyModel(3, "Ahmed", "Stanford University"),
            DummyModel(4, "Mahmoud", "Massachusetts Institute of Technology"),
            DummyModel(5, "Ali", "California Institute of Technology"),
            DummyModel(6, "John", "Princeton University"),
            DummyModel(7, "Alex", "Yale University"),
            DummyModel(8, "Karem", "Columbia University"),
            DummyModel(9, "Mohamed", "Duke University"),
            DummyModel(10, "Ali", "University of Cambridge"),
            DummyModel(11, "Omar", "University of Oxford"),
            DummyModel(12, "Ola", "University of California, Berkeley"),
            DummyModel(13, "Sagda", "University of Michigan"),
            DummyModel(14, "Alaa", "University of Pennsylvania"),
            DummyModel(15, "Kamal", "Cornell University"),
            DummyModel(16, "Abanob", "Carnegie Mellon University"),
            DummyModel(17, "Moris", "New York University"),
            DummyModel(18, "Misho", "University of California, Los Angeles"),
            DummyModel(19, "Samar", "Imperial College London"),
            DummyModel(20, "Nebula", "ETH Zurich")
        )
        adapter.setItems(
            dummyModels
        )


        adapter.onItemClickListener = object : OnItemClickListener<DummyModel, View?>() {
            override fun onItemClicked(view: View?, item: DummyModel, position: Int) {
                adapter.setSelected(position)
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
        adapter.onSelectedItemChange =
            BoilrBite.OnSelectedItemChange { oldPosition, oldItem, newPosition, newItem ->
                Toast.makeText(
                    this@MainActivity,
                    "new: " + newItem?.id.toString() + "  old: " + oldItem?.id.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}