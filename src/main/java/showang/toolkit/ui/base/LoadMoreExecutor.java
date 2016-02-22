package showang.toolkit.ui.base;

/**
 * Created by williamwang on 2015/8/4.
 */
public interface LoadMoreExecutor {
	void setLoadMoreEnable(boolean isEnable);
	void setLoadMoreListener(LoadMoreListener loadMoreListener);


	interface LoadMoreListener{
		void onLoadMore();
	}
}

