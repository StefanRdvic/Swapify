package com.yavs.swapify.ui.settings

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
import com.yavs.swapify.data.model.User
import com.yavs.swapify.utils.Platform

class SettingsAdapter(
    private var users: MutableList<User>,
    private val onConnectButtonClick: (Platform) -> Unit,
    private val onDisconnectButtonClick: (Platform) -> Unit
) :  RecyclerView.Adapter<ViewHolder>() {

    inner class UserViewHolder(private val view: View) : ViewHolder(view) {
        private val pfp: ImageView = view.findViewById(R.id.pfp)
        private val username: TextView = view.findViewById(R.id.username)
        private val disconnect: Button = view.findViewById(R.id.disconnect)
        private val platform: TextView = view.findViewById(R.id.platformLabel)

        fun onBind(user: User, position: Int, notify: () -> Unit) {
            Picasso.get().load(user.picture).into(pfp)
            username.text = view.context.getString(R.string.userFullName, user.name, user.lastName)
            platform.text = user.platform?.name ?: ""
            disconnect.setOnClickListener {
                onDisconnectButtonClick(user.platform!!)
                users[position] = User(platform = user.platform) // update to null user
                notify()
            }
        }
    }

    inner class AuthViewHolder(private val view: View) : ViewHolder(view) {
        private val authButton = view.findViewById<Button>(R.id.connectButton)
        private val platform = view.findViewById<TextView>(R.id.platformTextView)

        fun onBind(user: User) {
            platform.text = view.context.getString(R.string.platform, user.platform?.name)
            authButton.setOnClickListener {
                onConnectButtonClick(user.platform!!)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (users[position].id!==null) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_user, parent, false)
            )
            2 -> AuthViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_auth, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.onBind(users[position], position){
                notifyItemChanged(position)
            }

            is AuthViewHolder -> holder.onBind(users[position])
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}