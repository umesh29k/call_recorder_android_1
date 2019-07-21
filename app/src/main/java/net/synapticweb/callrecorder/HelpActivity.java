package net.synapticweb.callrecorder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import java.lang.reflect.Field;


public class HelpActivity extends TemplateActivity {
    ViewPager pager;
    HelpPagerAdapter adapter;
    static String[] content = new String[5];
    static String[] contentTitles = new String[5];
    static final int NUM_PAGES = 5;
    static final String TAG = "CallRecorder";

    @Override
    protected Fragment createFragment() { return null; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        content[0] = getResources().getString(R.string.help_overview);
        content[1] = getResources().getString(R.string.help_recording_calls);
        content[2] = getResources().getString(R.string.help_play_recordings);
        content[3] = getResources().getString(R.string.help_manage_recordings);
        content[4] = getResources().getString(R.string.help_licences);

        contentTitles[0] = getResources().getString(R.string.help_title1);
        contentTitles[1] = getResources().getString(R.string.help_title2);
        contentTitles[2] = getResources().getString(R.string.help_title3);
        contentTitles[3] = getResources().getString(R.string.help_title4);
        contentTitles[4] = getResources().getString(R.string.help_title5);

        setTheme();
        setContentView(R.layout.help_activity);
        pager = findViewById(R.id.help_pager);
        adapter = new HelpPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.help_tab_layout);
        tabLayout.setupWithViewPager(pager);

        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    static class HelpPagerAdapter extends FragmentPagerAdapter {
        HelpPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return HelpFragment.newInstance(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return contentTitles[position];
        }
    }

     static public class HelpFragment extends Fragment {
        static final String ARG_POSITION = "arg_pos";
        int position;

         static HelpFragment newInstance(int position) {
            HelpFragment fragment = new HelpFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_POSITION, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            position = getArguments() != null ? getArguments().getInt(ARG_POSITION) : 0;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_fragment, container, false);
//            WebView htmlText = view.findViewById(R.id.help_fragment_text);
//            htmlText.getSettings().setJavaScriptEnabled(true);
//            htmlText.setWebViewClient(new WebViewClient() {
//                public void onPageFinished(WebView view, String url) {
//                    view.loadUrl(
//                            "javascript:document.body.style.setProperty(\"background\", \"black\");"
//                    );
//                    view.loadUrl(
//                            "javascript:document.body.style.setProperty(\"color\", \"white\");"
//                    );
//                }
//            });
//            htmlText.loadData(content[position], "text/html", null);
            TextView text = view.findViewById(R.id.help_fragment_text);
            text.setText(CrApp.getSpannedText(content[position], new ImageGetter()));
            return view;
        }
    }

     static class ImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            Field plus;
            int res;
            Drawable image = null;
            try {
                //source = plus.jpg. NB: trebuie scos .jpg ca să meargă
//                plus = R.class.getDeclaredField(source);
//                res = plus.getInt(null); //proprietățile lui R sunt statice
                image = CrApp.getInstance().getResources().getDrawable(R.drawable.plus);
                image.setBounds(0, 0, 96, 96);
            }
            catch (Exception e) {
               Log.wtf(TAG, e.getMessage());
            }

            return image;
        }
    }
}