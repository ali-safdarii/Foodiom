package com.mehrsoft.foody.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient

import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants
import com.mehrsoft.foody.models.Result
import kotlinx.android.synthetic.main.fragment_instructions.*

class InstructionsFragment : Fragment(R.layout.fragment_instructions) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myBundle: Result = requireArguments().getParcelable(Constants.RECIPE_BUNDLE)!!

        instructions_webView.webViewClient = object :WebViewClient(){}
        val webSiteUrl:String=myBundle.sourceUrl
        instructions_webView.loadUrl(webSiteUrl)

    }


}