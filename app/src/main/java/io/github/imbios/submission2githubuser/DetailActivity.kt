package io.github.imbios.submission2githubuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.github.imbios.submission2githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        viewPagerConfig()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        supportActionBar?.elevation = 0f
    }


    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        dataUser.name?.let { setActionBarTitle(it) }
        binding.detailName.text = dataUser.name
        binding.detailUsername.text = dataUser.username
        binding.detailCompany.text = getString(R.string.company, dataUser.company)
        binding.detailLocation.text = getString(R.string.location, dataUser.location)
        binding.detailRepository.text = getString(R.string.repository, dataUser.repository)
        Glide.with(this)
            .load(dataUser.avatar)
            .into(binding.detailAvatar)
    }
}