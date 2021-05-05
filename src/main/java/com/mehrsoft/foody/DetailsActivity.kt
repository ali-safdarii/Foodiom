package com.mehrsoft.foody

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.foody.adapters.PagerAdapter
import com.example.foody.ui.fragments.ingredients.IngredientsFragment
import com.example.foody.ui.fragments.instructions.InstructionsFragment
import com.mehrsoft.foody.ui.fragments.overview.OverviewFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mehrsoft.foody.common.Constants.Companion.RECIPE_BUNDLE


class DetailsActivity : AppCompatActivity() {

    private val args: DetailsActivityArgs by navArgs()

    lateinit var resultBundle: Bundle
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager.isSaveEnabled=false
        resultBundle=Bundle()
        resultBundle.putParcelable(RECIPE_BUNDLE, args.result)

        val adapter =PagerAdapter(supportFragmentManager,lifecycle,resultBundle)

        adapter.addFragment(OverviewFragment(), getString(R.string.overview))
        adapter.addFragment(IngredientsFragment(), getString(R.string.ingredients))
        adapter.addFragment(InstructionsFragment(), getString(R.string.instructions))
        adapter.notifyDataSetChanged()
        viewPager.adapter=adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
/*

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        try {


            val adapter = PagerAdapter(
                resultBundle,
                fragments,
                titles,
                supportFragmentManager
            )

            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        } catch (e: Exception) {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /*private fun getSafeArgs() {
        arguments?.let {
            val args = ResultsFragmentArgs.fromBundle(it)
            teamChosen = args.choice
            if (teamChosen != null) {
                println("Safe Argument Received=${teamChosen?.strTeam}")
                updateUi(teamChosen)
            }
        }
    }*/
}