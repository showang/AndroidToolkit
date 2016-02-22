package showang.toolkit.ui.controller;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class ToolbarController {

	private Toolbar mToolbar;

	private ToolbarController(Toolbar toolbar) {
		mToolbar = toolbar;
	}

	public static ToolbarController init(Toolbar toolbar) {
		return new ToolbarController(toolbar);
	}

	public ToolbarController title(int resId) {
		mToolbar.setTitle(resId);
		return this;
	}

	public ToolbarController title(String title) {
		mToolbar.setTitle(title);
		return this;
	}

	public ToolbarController bgResource(int resId) {
		mToolbar.setBackgroundResource(resId);
		return this;
	}

	public ToolbarController menu(int resId, OnMenuItemClickListener listener) {
		mToolbar.inflateMenu(resId);
		mToolbar.setOnMenuItemClickListener(listener);
		return this;
	}

	public boolean hasItem(int itemId) {
		return mToolbar.getMenu().findItem(itemId) != null;
	}

	public Toolbar getToolbar() {
		return mToolbar;
	}

	public Menu getMenu() {
		return mToolbar.getMenu();
	}

	public MenuItem getMenuItem(int id) {
		return mToolbar.getMenu().findItem(id);
	}

	public ToolbarController backNavigation(OnClickListener onNavigationClickListener) {
		return navigation(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha, onNavigationClickListener);
	}

	public ToolbarController navigation(int iconResId, OnClickListener onNavigationClickListener) {
		mToolbar.setNavigationIcon(iconResId);
		mToolbar.setNavigationOnClickListener(onNavigationClickListener);
		return this;
	}

	public ToolbarController visible(boolean isVisible) {
		mToolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
		return this;
	}

	public ToolbarController bgColor(int colorRes) {
		mToolbar.setBackgroundColor(ContextCompat.getColor(mToolbar.getContext(), colorRes));
		return this;
	}
}
