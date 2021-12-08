package cn.itcast.experiment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.experiment.MyData.BookItem;
import cn.itcast.experiment.MyData.DataBank;

public class MainActivity extends AppCompatActivity {

//    private TabLayout mTabLayout;
//    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private List<Fragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器
    private BookItemFragment frgBook = new BookItemFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabTitles = new String[]{ "Book", "News", "Consumer" };

        NewsItemFragment frgNew = new NewsItemFragment();
        ConsumerItemFragment frgConsumer = new ConsumerItemFragment();
        mDatas.add(frgBook);
        mDatas.add(frgNew);
        mDatas.add(frgConsumer);

        ViewPager2 viewPagerFragments=findViewById(R.id.viewpager2_content);
        viewPagerFragments.setAdapter(new MyFragmentAdpater(this, mDatas));

        TabLayout tabLayoutHeader= findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayoutHeader, viewPagerFragments, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        });
        tabLayoutMediator.attach();

        //ViewPage2选中改变监听
        viewPagerFragments.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        //TabLayout的选中改变监听
        tabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    private class MyFragmentAdpater extends FragmentStateAdapter {
        List<Fragment> mDatas = new ArrayList<>();

        public MyFragmentAdpater(@NonNull FragmentActivity fragmentActivity, List<Fragment> mDatas) {
            super(fragmentActivity);
            this.mDatas = mDatas;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position)
            {

                case 0:
                    return BookItemFragment.newInstance( frgBook );
                case 1:
                    return WebViewFragment.newInstance();
                default:
                    return MapFragment.newInstance();
            }
//            return mDatas.get( position );
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }




}