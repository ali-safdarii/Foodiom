package com.mehrsoft.foody.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants.Companion.BASE_IMAGE_URL
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.models.ExtendedIngredient
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import java.util.*


class IngredientsAdapter :
    RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {
    private var ingredientList = emptyList<ExtendedIngredient>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredients_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.ingredient_name.text = ingredientList[position].name.capitalize(Locale.ROOT)
        holder.itemView.ingredient_amount.text = ingredientList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientList[position].unit
        holder.itemView.ingredient_consistency.text = ingredientList[position].consistency
        holder.itemView.ingredient_original.text = ingredientList[position].original

    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}