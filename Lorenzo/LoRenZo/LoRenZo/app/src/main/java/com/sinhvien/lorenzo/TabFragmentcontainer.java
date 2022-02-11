package com.sinhvien.lorenzo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabFragmentcontainer extends FragmentStatePagerAdapter {

    public TabFragmentcontainer(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFragment();
            case 1:
                return new FashionFragment();
            case 2:
                return new HardwareFragment();
            case 3:
                return new ToyFragment();
            default:
                return new AllFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position)
        {
            case 0:
                title="Tất cả";
                break;
            case 1:
                title="Phụ kiện thời trang";
                break;
            case 2:
                title="Linh kiện điện tử";
                break;
            case 3:
                title="Đồ chơi";
                break;
        }
        return title;
    }
}
