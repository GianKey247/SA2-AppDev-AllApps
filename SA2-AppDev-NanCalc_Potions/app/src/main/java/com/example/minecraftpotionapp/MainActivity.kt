package com.example.minecraftpotionapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.ceil
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    private lateinit var undo_imageButton : ImageButton
    private lateinit var information_Button : ImageButton
    private lateinit var trash_imageButton : ImageButton
    private lateinit var potion_icon_imageView : ImageView
    private lateinit var potion_name_textView : TextView
    private lateinit var status_effect_icon_imageView : ImageView
    private lateinit var ingredrient_tableLayout : TableLayout
    private lateinit var ingredient_glowstone_imageButton : ImageButton
    private lateinit var ingredient_redstone_imageButton : ImageButton
    private lateinit var ingredient_gunpowder_imageButton : ImageButton
    private lateinit var ingredient_dragonsBreath_imageButton : ImageButton
    val potionsManager = PotionsManager()
    val listOfIngredients=Ingredient.entries.toMutableList()
    var maxItemsPerRow: Int = 6
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        undo_imageButton = findViewById(R.id.undo_button)
        information_Button = findViewById(R.id.InformationPopUp_Button)
        trash_imageButton = findViewById(R.id.trash_button)
        potion_icon_imageView = findViewById(R.id.potion_icon)
        potion_name_textView = findViewById(R.id.Potion_Name)
        status_effect_icon_imageView = findViewById(R.id.status_effect_icon)
        ingredient_glowstone_imageButton = findViewById(R.id.Ingredient_Glowstone)
        ingredient_redstone_imageButton = findViewById(R.id.Ingredient_Redstone)
        ingredient_gunpowder_imageButton = findViewById(R.id.Ingredient_Gunpowder)
        ingredient_dragonsBreath_imageButton = findViewById(R.id.Ingredient_DragonsBreath)
        ingredrient_tableLayout = findViewById(R.id.Ingredient_Table)
        listOfIngredients.remove(Ingredient.netherwart)
        listOfIngredients.remove(Ingredient.glowstone)
        listOfIngredients.remove(Ingredient.redstone)
        listOfIngredients.remove(Ingredient.gunpowder)
        listOfIngredients.remove(Ingredient.dragons_breath)
        val potion=potionsManager.getCurrentPotion()
        set_elements(potion)

        undo_imageButton.setOnClickListener() {
            set_elements(potionsManager.undo())
        }

        trash_imageButton.setOnClickListener() {
            set_elements(potionsManager.clearPotion())
        }

        information_Button = findViewById(R.id.InformationPopUp_Button)
        information_Button.setOnClickListener(){
            var intent = Intent(this@MainActivity, InstructionsPopup::class.java)
            startActivity(intent)
        }

    }
    fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    private fun set_elements(potion:Potion){
        potion_icon_imageView.setImageResource(potion.getDrawable())
        status_effect_icon_imageView.setImageResource(potion.effect_icon)
        potion_name_textView.setText("${potion.getPotionName()} \n ${potion.getDuration()}")
        ingredrient_tableLayout.removeAllViews()
        set_table_elements(ingredrient_tableLayout, potion)
        if ( Ingredient.redstone in potion.validIngredients()){
            configureImageButton(ingredient_redstone_imageButton, Ingredient.redstone)
        } else
        {
            disableImageButton(ingredient_redstone_imageButton)
        }
        if (Ingredient.glowstone in potion.validIngredients()) {
            configureImageButton(ingredient_glowstone_imageButton, Ingredient.glowstone)
        }
        else{
            disableImageButton(ingredient_glowstone_imageButton)
        }
        if (potion.isSplashPotion && !potion.isLingeringPotion) {
            configureImageButton(ingredient_dragonsBreath_imageButton, Ingredient.dragons_breath)

        } else {
            disableImageButton(ingredient_dragonsBreath_imageButton)
        }

        if (potion.isSplashPotion){
            disableImageButton(ingredient_gunpowder_imageButton)
        }
        else{
            configureImageButton(ingredient_gunpowder_imageButton, Ingredient.gunpowder)
        }
    }

    private fun configureImageButton(imageButton:ImageButton,ingredient: Ingredient){
        imageButton.setColorFilter(Color.TRANSPARENT)
        imageButton.setOnClickListener(){
            Toast.makeText(this, "You selected : ${ingredient.name}", Toast.LENGTH_SHORT).show()
            val newpotion=potionsManager.brew(ingredient)
            set_elements(newpotion)
        }
    }

    private fun disableImageButton(imageButton:ImageButton){
        imageButton.setColorFilter(R.color.black)
        imageButton.setOnClickListener(null)
    }

    private fun addFrameLayouts(tableRow: TableRow,start:Int, end:Int, potion: Potion) {

        for (i in start until  end) {
            val frameLayout = FrameLayout(this)
            frameLayout.layoutParams = TableRow.LayoutParams(
                dpToPx(60)  ,
                dpToPx(60),
                1.0f
            ).apply {
                gravity = Gravity.CENTER
            }
            frameLayout.setPadding(6, 0, 0, 0)

            val imageView = ImageView(this)
            imageView.layoutParams = FrameLayout.LayoutParams(
                dpToPx(60),
                dpToPx(60)
            ).apply {
                gravity = Gravity.CENTER
            }
            imageView.setImageResource(R.drawable.item_slot)

            val imageButton = ImageButton(this)
            imageButton.layoutParams = FrameLayout.LayoutParams(
                dpToPx(57),
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            }
            imageButton.scaleType=ImageView.ScaleType.FIT_CENTER
            imageButton.setBackgroundColor(Color.TRANSPARENT)
            // Set different image resources for different items based on your logic
            val ingredient = listOfIngredients[i]

//            println("Index ${i} | Name ${ingredient.name}")
            val ingredient_Icon = ingredient.resourceID

            imageButton.setImageResource(ingredient_Icon)
            imageButton.setOnClickListener(){
                Toast.makeText(this,"You Selected ${ingredient.name}",Toast.LENGTH_SHORT).show()
            }
            if (ingredient in potion.validIngredients()){
                configureImageButton(imageButton, ingredient)
            }
            else{
                disableImageButton(imageButton)
            }



            frameLayout.addView(imageView)
            frameLayout.addView(imageButton)
            tableRow.addView(frameLayout)
        }
    }

    private fun set_table_elements(ingredient_tableLayout:TableLayout,potion: Potion){
        var numberOfRows: Int = ceil(listOfIngredients.size.toDouble() / maxItemsPerRow).toInt()
//        println("Number of rows: $numberOfRows")
        for (row in 0 until  numberOfRows) {
            val tableRow = TableRow(this)
            tableRow.setLayoutParams(TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT))
            tableRow.gravity=Gravity.CENTER
            val start = row * maxItemsPerRow
            val end = min(start + maxItemsPerRow, listOfIngredients.size)
//            println("start : ${start} | End : ${end}")
            addFrameLayouts(tableRow,start,end,potion)
            ingredient_tableLayout.addView(tableRow)
        }
    }
}


