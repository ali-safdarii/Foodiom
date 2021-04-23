package com.mehrsoft.foody.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import kotlinx.android.synthetic.main.recipes_row_layout.view.*

class RecipesAdapter(private var result:List<Result>, private val context:Context) : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {



    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        fun bind(result: Result,context: Context){



            itemView.apply {
                Glide.with(context).load(result.image).into(recipe_imageView)
                title_textView.text=result.title
                description_textView.text=result.summary
                heart_textView.text= result.aggregateLikes.toString()
                clock_textView.text=result.readyInMinutes.toString()



                if(result.vegan){
                    leaf_textView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )

                    leaf_imageView.setColorFilter(
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