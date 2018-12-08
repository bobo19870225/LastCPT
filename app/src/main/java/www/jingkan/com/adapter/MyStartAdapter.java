/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.ViewGroup;

public class MyStartAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentList;
	public MyStartAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyStartAdapter(FragmentManager fm,List<Fragment> fragmentList){
		super(fm);
		this.fragmentList=fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	
	
}
