package com.mehrsoft.foody.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.data.database.entities.FavoritesEntity
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import com.mehrsoft.foody.ui.fragments.recipes.RecipesFragmentDirections


class FavoriteRecipesAdapter() : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favoriteList= emptyList<FavoritesEntity>()

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        fun bind(favoritesEntity: FavoritesEntity){

            val recipeImageView = itemView.findViewById<ImageView>(R.id.favorite_recipe_imageView)
            val titleTextView = itemView.findViewById<TextView>(R.id.favorite_title_textView)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.favorite_description_textView)
            val heartTextView = itemView.findViewById<TextView>(R.id.favorite_heart_textView)
            val clockTextView = itemView.findViewById<TextView>(R.id.favorite_clock_textView)
            val leafTextView = itemView.findViewById<TextView>(R.id.favorite_leaf_textView)
            val leafImageView = itemView.findViewById<ImageView>(R.id.favorite_leaf_imageView)


            itemView.apply {

                recipeImageView.load(favoritesEntity.result.image) {
                    crossfade(600)
                    error(R.drawable.error_placeholder)
                }

                titleTextView.text=favoritesEntity.result.title
                descriptionTextView.text=favoritesEntity.result.summary
                heartTextView.text= favoritesEntity.result.aggregateLikes.toString()
                clockTextView.text=favoritesEntity.result.readyInMinutes.toString()



                if(favoritesEntity.result.vegan){
                    leafTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )

                    leafImageView.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.favorite_recipes_row_layout,parent,false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite=favoriteList[position]
        holder.bind(favorite)


    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>){
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteList, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

}