package com.ptit.filmdictionary.ui.actor;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.databinding.ActivityActorBinding;
import com.ptit.filmdictionary.ui.search.SearchActivity;

public class ActorActivity extends AppCompatActivity {
    public static final String BUNDLE_ACTOR_KEY = "BUNDLE_ACTOR_KEY";
    public static final String ETRA_ACTOR = "com.ptit.filmdictionary.extras.EXTRA_ACTOR";
    private static final int DEFAULT_SCROLL_RANGE = -1;
    private static final CharSequence NONE_TITLE = " ";
    private Actor mActor;
    private ActivityActorBinding mBinding;
    private ViewPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.TransparentStatusTheme);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_actor);
        receiveData();
        initActionBar();
        hideExpandedTittle();
        setupViewPagerAdapter();
    }

    private void setupViewPagerAdapter() {
        mPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mPageAdapter);

        mBinding.viewPager
                .addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayoutActor));
        mBinding.tabLayoutActor
                .addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPager));
    }

    private void hideExpandedTittle() {
        mBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = DEFAULT_SCROLL_RANGE;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == DEFAULT_SCROLL_RANGE) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbarActor.setTitle(mActor.getName());
                    mBinding.toolbarActor.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
                    mBinding.imageSearch.setColorFilter(Color.BLACK);
                    isShow = true;
                } else if (isShow) {
                    mBinding.collapsingToolbarActor.setTitle(NONE_TITLE);
                    mBinding.toolbarActor.setNavigationIcon(R.drawable.ic_arrow_white_24dp);
                    mBinding.imageSearch.setColorFilter(Color.WHITE);
                    isShow = false;
                }
            }
        });
    }

    private void initActionBar() {
        setSupportActionBar(mBinding.toolbarActor);
        mBinding.toolbarActor.setNavigationIcon(R.drawable.ic_arrow_white_24dp);
        mBinding.toolbarActor.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void receiveData() {
        Bundle bundle = getIntent().getBundleExtra(ETRA_ACTOR);
        mActor = bundle.getParcelable(BUNDLE_ACTOR_KEY);
        mBinding.setActor(mActor);
    }

    public static Intent getIntent(Context context, Actor actor) {
        Intent intent = new Intent(context, ActorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ACTOR_KEY, actor);
        intent.putExtra(ETRA_ACTOR, bundle);
        return intent;
    }

    public void onSearchClick(View view) {
        startActivity(SearchActivity.getIntent(this));
    }

    public String getActorId() {
        return mActor.getId();
    }
}
