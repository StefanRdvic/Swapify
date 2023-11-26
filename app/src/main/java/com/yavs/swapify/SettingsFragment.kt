package com.yavs.swapify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.yavs.swapify.service.AuthService
import com.yavs.swapify.service.DeezerService
import com.yavs.swapify.utils.Platform


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "Settings"
private const val ARG_PARAM2 = "title"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    fun initView(view: View){
        val authService = AuthService(view.context)
        val deezerService = DeezerService()
        val layout = view.findViewById<ConstraintLayout>(R.id.SettingsLayout)
        val lp = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,  // Width of TextView
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        layout.addView(TextView(layout.context).apply {
            text= "Settings"
            textSize= 40F
            layoutParams = lp
        })
        for (platform in Platform.entries){
            var token = authService.getAuthToken(platform.accountName)
            println(" $token $platform")
            val ll = LinearLayout(view.context).apply { layoutParams = lp }

            if(token!=null){
                val text = TextView(ll.context)
                text.text="Connected to ${platform.accountName} ${deezerService.getToken(token)}"
            }else{
                val b = Button(ll.context)
                b.text="Connect to ${platform.accountName}"
                when (platform){
                    Platform.Deezer -> b.setOnClickListener {
                        val url = deezerService.getLoginUrl()
                        val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browse)
                    }
                }

                ll.addView(b)
            }
            layout.addView(ll)
        }


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SwapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SwapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}