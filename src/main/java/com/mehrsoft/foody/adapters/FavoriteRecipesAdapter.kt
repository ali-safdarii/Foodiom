package com.mehrsoft.foody.adapters
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.data.database.entities.FavoritesEntity
import com.mehrsoft.foody.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.mehrsoft.foody.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*


class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteList = emptyList<FavoritesEntity>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var myViewHolder = arrayListOf<MyViewHolder>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(favoritesEntity: FavoritesEntity) {

            val recipeImageView = itemView.findViewById<ImageView>(R.id.favorite_recipe_imageView)
            val titleTextView = itemView.findViewById<TextView>(R.id.favorite_title_textView)
            val descriptionTextView =
                itemView.findViewById<TextView>(R.id.favorite_description_textView)
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
        myViewHolder.add(holder)
        val favorite = favoriteList[position]
        rootView = holder.itemView.rootView
        holder.bind(favorite)

        //Single ClickListener
        holder.itemView.setOnClickListener {

            if (multiSelection)
                applySelection(holder, favorite)
            else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        favorite.result
                    )
                holder.itemView.findNavController().navigate(action)

            }

        }

        holder.itemView.setOnLongClickListener {

            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, favorite)
                true
            } else {
                multiSelection = false
                false
            }


        }

    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    private fun changeStatusColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipes: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipes)) {
            selectedRecipes.remove(currentRecipes)
            changeRecipesStyles(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipes)
            changeRecipesStyles(holder, R.color.contextualBackgroundColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipesStyles(
        holder: MyViewHolder,
        backgroundColor: Int,
        strokeColor: Int,


        ) {


        holder.itemView.favorite_row_cardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

        holder.itemView.innerConstriantLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )


/*
        holder.itemView.favorite_title_textView.setTextColor(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )
        holder.itemView.favorite_description_textView.setTextColor(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )
        holder.itemView.favorite_heart_textView.setTextColor(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )
        holder.itemView.favorite_clock_textView.setTextColor(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )
        holder.itemView.favorite_leaf_textView.setTextColor(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )

        holder.itemView.favorite_leaf_imageView.setColorFilter(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )

        holder.itemView.favorite_clock_imageView.setColorFilter(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )


        holder.itemView.favorite_heart_imageView.setColorFilter(
            ContextCompat.getColor(
                requireActivity,
                contextualItemColor
            )
        )
*/


    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }

    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {

        actionMode?.menuInflater?.inflate(R.menu.favorite_contexual_menu, menu)
        changeStatusColor(R.color.contextualStatusBarColor)
        mActionMode = actionMode!!
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {

        if (menu!!.itemId == R.id.delete_favorite_recipe_menu) {

            selectedRecipes.forEach {

                mainViewModel.deleteFavoriteRecipe(it)
            }

            showSnackBar("${selectedRecipes.size} Recipe/s removed.")
            multiSelection=false
            selectedRecipes.clear()
            actionMode?.finish()

        }
        return true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolder.forEach { holder ->
            changeRecipesStyles(holder, R.color.cardBackgroundColor, R.color.strokeColor)

        }
        multiSelection = false
        selectedRecipes.clear()
        changeStatusColor(R.color.statusBarColor)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteList, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}