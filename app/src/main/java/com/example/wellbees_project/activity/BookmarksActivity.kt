package com.example.wellbees_project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.wellbees_project.R
import com.example.wellbees_project.adapters.ViewPagerAdapter
import com.example.wellbees_project.fragments.NewsDetailFragment
import com.example.wellbees_project.fragments.SourceFragment
import com.google.android.material.tabs.TabLayout

class BookmarksActivity : AppCompatActivity() {

    lateinit var tabs: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)


        setUpTabs()
    }

    //buradaki alanda bookmark activitymin appbarında bulunan tabların ayarlarını yapıyorum
    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SourceFragment(), "Sources")
        adapter.addFragment(NewsDetailFragment(), "News Bookmarks")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_source_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_short_text_24)
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }

}