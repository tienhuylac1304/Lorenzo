package com.sinhvien.lorenzo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sinhvien.lorenzo.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    SearchView searchView;
    ListView listView;
    TabLayout tabLayout,dataViewType;
    ViewPager viewPager;
    DBHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        tabLayout=view.findViewById(R.id.tabContainer);
        viewPager=view.findViewById(R.id.tabFragment_container);
        searchView=view.findViewById(R.id.searchView);
        listView=view.findViewById(R.id.searchViewResult);
        db=new DBHelper(getActivity());

        TabFragmentcontainer tabAdapter=new TabFragmentcontainer(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdapter);

        tabLayout.setupWithViewPager(viewPager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i=new Intent(getActivity(),SearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("stringdata",query);

                i.putExtra("data",bundle);
                getActivity().startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.matches(""))
                {
                    ArrayAdapter adapter1=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,new ArrayList());
                    adapter1.getFilter().filter(newText);
                    listView.setAdapter(adapter1);
                }
                else {
                    ArrayAdapter adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,LoadDataForListView());
                    adapter.getFilter().filter(newText);
                    listView.setAdapter(adapter);
                }

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(),SearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("stringdata",listView.getItemAtPosition(position).toString());

                i.putExtra("data",bundle);
                getActivity().startActivity(i);
            }
        });
        //29/12/2020

        return  view;
    }
    private ArrayList<String> LoadDataForListView()
    {
        ArrayList<String>temp=new ArrayList<>();

        temp=db.getAllProductName();

        ArrayList<String> listType=new ArrayList<String>();
        listType=db.getAllProctType();

        for(int i=0;i<listType.size();i++)
        {
            temp.add(listType.get(i));
        }
        ArrayList<String> result = new ArrayList<String>();
        for (String element : temp) {

            if (!result.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }

}
