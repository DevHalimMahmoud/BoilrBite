[![](https://jitpack.io/v/abdomi7/BoilrBite.svg)](https://jitpack.io/#abdomi7/BoilrBite)

# BoilrBite

This library provides an out-of-the-box solution for implementing a RecyclerView in your Android app
without the need to create a custom adapter. By using this library, you can simplify the development
process and reduce the amount of boilerplate code needed, allowing you to focus on building great
user experiences. With its intuitive API, this library makes it easy to handle data updates and
implement common RecyclerView features such as item click listeners and view holders. Say goodbye to
tedious adapter implementations and hello to streamlined RecyclerView development with our library.

## Features

BoilrBite has many powerful features, including:

* Latest ListAdapter: Built on top of the latest ListAdapter for maximum performance.
* Efficient Updates: Support for DiffUtil to efficiently update the list data with minimal work.
* Clickable Views: Ability to listen for clicks on the container itself or on individual container components.
* Selected Item Callbacks: onSelectedItemChanged callback to listen for changes in the selected item
* Easy Data Manipulation: addOrUpdateItems method to add or update items in the list.


## Usage

### Step 1. Add the JitPack repository to your build file

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2: Add the library dependency

Add the following dependency to your app's build.gradle file:

```
dependencies {
	        implementation 'com.github.abdomi7:BoilrBite:1.0.3'
}
```

### Step 3: Start using BoilrBite

Create an instance of BoilrBite adapter and set it to your RecyclerView:

```
    val adapter = BoilrBite.createBoilrBiteAdapter(
            items = mutableListOf<DummyModel>(), // List of items to be displayed
            layoutResId = R.layout.item, // Layout resource id
            clickableViewIds = setOf(R.id.btn_dummy, R.id.tv_name, R.id.tv_university), // Set of clickable view ids
            compareItems = { old, new -> old == new }, // Compare items to check for changes
            compareContents = { old, new -> old.id == new.id }, // Compare contents to check for changes
            bind = { view, item -> // Bind the item to the view
                val binding = DataBindingUtil.bind<ItemBinding>(view)
                binding?.tvName?.text = item.name
                binding?.tvUniversity?.text = item.university
            })
            
    binding.rvMain.adapter = adapter // Set the adapter to the RecyclerView

```

### That is it!

## Common functions for data manipulation

BoilrBite provides many useful functions for data manipulation:


```
    
    adapter.setItems(listOf(DummyModel())) // Set the list of items

    adapter.addOrUpdateItems(listOf(DummyModel())) // Add or update items in the list

    adapter.addOrUpdateItem(DummyModel()) // Add or update a single item in the list

    adapter.clearItems() // Clear all items in the list

    adapter.setSelectedItem(0) // Set the selected item

    adapter.getSelected() // Get the selected item position

    adapter.getSelectedItem() // Get the selected item

    adapter.removeItem(0) // Remove item at index 0

    adapter.removeItems() // Remove items from the list

    adapter.clear() // Clear the list

    adapter.indexOf(DummyModel()) // Get the index of an item


```

## Event Handling

BoilrBite makes event handling easy with the following features:

```
        adapter.onItemClickListener = object : OnItemClickListener<DummyModel, View?>() {
            override fun onItemClicked(view: View?, item: DummyModel, position: Int) {
                Toast.makeText(this@MainActivity, "Item clicked: $item", Toast.LENGTH_SHORT).show()
            }
        } // Set the item click listener


        adapter.onSelectedItemChange =
            BoilrBite.OnSelectedItemChange<DummyModel> { oldPosition, oldItem, newPosition, newItem ->
                Toast.makeText(
                    this@MainActivity,
                    "new: " + newItem?.id.toString() + "  old: " + oldItem?.id.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } // Set the selected item change listener

```

## Conclusion

BoilrBite is a lightweight and easy-to-use library that simplifies the development process of
implementing a RecyclerView in your Android app. By reducing boilerplate code and providing an
intuitive API, it allows you to focus on building great user experiences. Try it out today and see
how it can streamline your RecyclerView development process.


