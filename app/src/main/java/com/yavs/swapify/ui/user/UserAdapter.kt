package com.yavs.swapify.ui.user

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.yavs.swapify.R
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.model.User
import com.yavs.swapify.service.PlatformManager

class UserAdapter(
    private val context: Context,
    private var accounts: MutableList<User>,
    private val tokenManager: TokenManager
) :  RecyclerView.Adapter<ViewHolder>() {
    class UserViewHolder(view: View) : ViewHolder(view) {
        val pfp: ImageView = view.findViewById(R.id.pfp)
        val username: TextView = view.findViewById(R.id.username)
        val disconnect: Button = view.findViewById(R.id.disconnect)
        val platform: TextView = view.findViewById(R.id.platformLabel)
    }
    class ConnectViewHolder(view: View) : ViewHolder(view) {
        val connect: Button = view.findViewById(R.id.connect)
    }

    override fun getItemViewType(position: Int): Int {
        return if(accounts[position].id!==null) 1 else 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> {
                val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_user, parent, false)
                UserViewHolder(adapterLayout)
            }
            2 -> {
                val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_connection, parent, false)
                val c = ConnectViewHolder(adapterLayout)
                c
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val user = accounts[position]
                Picasso.get().load(user.picture).into(holder.pfp)
                holder.pfp.setOnClickListener{
                    this.tokenManager.removeToken(context.applicationContext, user.platform!!)
                }
                holder.platform.text= user.platform!!.accountName
                holder.username.text= context.getString(R.string.userFullName, user.name, user.lastName)
                holder.disconnect.setOnClickListener {
                    tokenManager.removeToken(context,user.platform!!)
                    accounts[position]= User(platform = user.platform)
                    notifyItemChanged(position)
                }
            }

            is ConnectViewHolder -> {
                val plat = accounts[position].platform
                holder.connect.text = context.getString(R.string.connect_to, plat)
                holder.connect.setOnClickListener {
                    val url = PlatformManager.getService(plat!!).getOAuthUrl()
                    val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(browse)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return accounts.size
    }

}