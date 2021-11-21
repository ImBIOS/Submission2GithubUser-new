package io.github.imbios.submission2githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.github.imbios.submission2githubuser.databinding.ItemCardviewUserBinding


class CardViewUserAdapter(private val listUserData: ArrayList<UserData>) :
    RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder>() {

    private var onItemClickCallback: CardViewUserAdapter.OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: CardViewUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewUserAdapter.CardViewViewHolder {
        val binding =
            ItemCardviewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewUserAdapter.CardViewViewHolder, position: Int) {
        holder.bind(listUserData[position])
    }

    override fun getItemCount(): Int = listUserData.size

    inner class CardViewViewHolder(private val binding: ItemCardviewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(userData.avatar)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgItemPhoto)

                binding.txtUsername.text = userData.username
                binding.txtName.text = userData.name
                binding.txtCompany.text = userData.company
                binding.txtLocation.text = userData.location

//                btnSetFavorite.setOnClickListener {
//                    Toast.makeText(
//                        itemView.context,
//                        "Favorite ${userData.name}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
                btnSetShare.setOnClickListener {
                    Toast.makeText(itemView.context, "Share ${userData.name}", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Cool! ${userData.name} working in ${userData.company}"
                    )
                    intent.type = "text/plain"
                    itemView.context.startActivity(Intent.createChooser(intent, "Send To"))
                }
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih ${userData.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(userData) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }
}