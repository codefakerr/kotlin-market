package com.example.market.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.market.R
import com.example.market.chatdetail.ChatRoomActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var detailTitleTextView: TextView
    private lateinit var detailPriceTextView: TextView
    private lateinit var detailDescriptionTextView: TextView
    private val REQUEST_CODE_EDIT_ARTICLE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailTitleTextView = findViewById(R.id.detailTitleTextView)
        detailPriceTextView = findViewById(R.id.detailPriceTextView)
        detailDescriptionTextView = findViewById(R.id.detailDescriptionTextView)
        val detailImageView: ImageView = findViewById(R.id.detailImageView)
        val detailChatButton: Button = findViewById(R.id.detailChatButton)
        val detailEditButton: Button = findViewById(R.id.detailEditButton)

        val chatKey = intent.getStringExtra("chatKey")
        val sellerId = intent.getStringExtra("sellerId") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val price = intent.getStringExtra("price") ?: ""
        val priceWithWon = "$price"+"원"
        val description = intent.getStringExtra("description") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        detailTitleTextView.text = title
        detailPriceTextView.text = priceWithWon
        detailDescriptionTextView.text = description

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.han_mask)
            .error(R.drawable.han_mask)
            .into(detailImageView)

        val isSeller = intent.getBooleanExtra("isSeller", false)
        if (isSeller) {
            detailChatButton.visibility = View.GONE
            detailEditButton.visibility = View.VISIBLE

            detailEditButton.setOnClickListener {
                val intent = Intent(this, EditArticleActivity::class.java)
                intent.putExtra("chatKey", chatKey)
                intent.putExtra("title", title)
                intent.putExtra("price", price)
                intent.putExtra("description", description)
                intent.putExtra("imageUrl", imageUrl)
                startActivityForResult(intent, REQUEST_CODE_EDIT_ARTICLE)
            }

        } else {
            detailChatButton.visibility = View.VISIBLE
            detailEditButton.visibility = View.GONE

            detailChatButton.setOnClickListener {
                // 채팅 버튼 클릭 시 처리
                val chatKey = intent.getStringExtra("chatKey")

                // ChatRoomActivity를 시작하고 필요한 정보를 전달합니다.
                val intent = Intent(this, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", chatKey)
                startActivity(intent)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE_EDIT_ARTICLE && resultCode == Activity.RESULT_OK) {
            val updatedTitle = data?.getStringExtra("title")
            val updatedPrice = data?.getStringExtra("price")
            val updatedDescription = data?.getStringExtra("description")
            val updatedPriceWithWon = "$updatedPrice"+"원"

            detailTitleTextView.text = updatedTitle
            detailPriceTextView.text = updatedPriceWithWon
            detailDescriptionTextView.text = updatedDescription
        }
    }
}
