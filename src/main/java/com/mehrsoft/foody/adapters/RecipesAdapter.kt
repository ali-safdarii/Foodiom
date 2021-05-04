package com.mehrsoft.foody.adapters
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import com.mehrsoft.foody.ui.fragments.recipes.RecipesFragmentDirections


class RecipesAdapter(private var result:List<Result>, private val context:Context) : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        fun bind(result: Result,context: Context){

            val titleTextView = itemView.findViewById<TextView>(R.id.title_textView)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.description_textView)
            val heartTextView = itemView.findViewById<TextView>(R.id.heart_textView)
            val clockTextView = itemView.findViewById<TextView>(R.id.clock_textView)
            val leafTextView = itemView.findViewById<TextView>(R.id.leaf_textView)
            val recipeImageView = itemView.findViewById<ImageView>(R.id.recipe_imageView)
            val leafImageView = itemView.findViewById<ImageView>(R.id.leaf_imageView)


            itemView.apply {
                Glide.with(context).load(result.image).into(recipeImageView)
                titleTextView.text=result.title
                descriptionTextView.text=result.summary
                heartTextView.text= result.aggregateLikes.toString()
                clockTextView.text=result.readyInMinutes.toString()



                if(result.vegan){
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

        val layoutInflater=LayoutInflater.from(context).inflate(R.layout.recipes_row_layout,parent,false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipeResult=result[position]
        holder.bind(result = recipeResult,context = context)

        holder.itemView.setOnClickListener {


            try {

                val action=RecipesFragmentDirections.
                actionRecipesFragmentToDetailsActivity(recipeResult)
                it.findNavController().navigate(action)

                Log.d(TAG, "onBindViewHolder: Called")

            }catch (e:Exception){

                Log.d(TAG, e.message.toString())
            }



        }
    }

    override fun getItemCount(): Int {
        return result.size
    }


    fun setData(newData: FoodRecipe){
        val recipesDiffUtil =
            RecipesDiffUtil(result, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        result = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}