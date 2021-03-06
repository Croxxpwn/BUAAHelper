package com.ourbuaa.buaahelper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.lang.reflect.Field;
import java.util.List;
/**
 * Created by alan_yang on 2017/1/21.
 */

public class BUAA_RecyclerViewAdapter extends SwipeMenuAdapter<BUAA_RecyclerViewAdapter.ListItemViewHolder> {


    private  List<CommonItemForList> items;
    private  NoticeListFragment.OnListFragmentInteractionListener Listener;
    private OnClickListener onClickListener;
    private ContentProvider provider;
    private GarbageCollector garbageCollector;
    BUAA_RecyclerViewAdapter(ContentProvider provider) {

        if (provider == null ) {
            throw new IllegalArgumentException(
                    "Data must not be null");
        }
        this.provider = provider;
        this.items = provider.getDataList();
        this.Listener = null;
    }
    public GarbageCollector getGarbageCollector() {
        return garbageCollector;
    }

    public void setGarbageCollector(GarbageCollector garbageCollectioner) {
        this.garbageCollector = garbageCollectioner;
    }
    public OnClickListener getOnClickListener() {
        return onClickListener;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public NoticeListFragment.OnListFragmentInteractionListener getListener() {
        return Listener;
    }

    public void setListener(NoticeListFragment.OnListFragmentInteractionListener listener) {
        Listener = listener;
    }

    public ContentProvider getProvider() {
        return provider;
    }

    public void setProvider(ContentProvider provider) {
        this.provider = provider;
    }

    public void setOnListFragmentInteractionListener(NoticeListFragment.OnListFragmentInteractionListener mlistener)
    {
            this.Listener = mlistener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_demo,
                        parent,
                        false);
        return itemView;
    }

    @Override
    public ListItemViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ListItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(
            final ListItemViewHolder viewHolder, int position) {
        viewHolder.itemForList = items.get(position);
        viewHolder.label.setText(viewHolder.itemForList.label);
        if (viewHolder.itemForList.isread()) viewHolder.label.setTextColor(Color.GRAY);
        String dateStr = DateUtils.formatDateTime(
                viewHolder.label.getContext(),
                viewHolder.itemForList.ReceiveTime.getTime(),
                DateUtils.FORMAT_ABBREV_ALL);
        viewHolder.dateTime.setText(dateStr);
        try {
            Field field = R.mipmap.class.getDeclaredField(viewHolder.itemForList.getPathToImage());
            field.setAccessible(true);
            R.mipmap mipmap = new R.mipmap();
            Object oId = field.get(mipmap);
            int id =  (Integer) oId;
            viewHolder.icon.setImageResource(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

     class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView label;
         TextView dateTime;
         ImageView icon;
         View view;
         CommonItemForList itemForList;
         RelativeLayout mItemLayout;
         LinearLayout ContainerLayout,mSwipeMenuLayout;

   //      private int mScreenWidth;	// 屏幕宽度
    //     private int mDownX;			// 按下点的x值
    //     private int mDownY;			// 按下点的y值
    //     private int mDeleteBtnWidth;// 删除按钮的宽度

//         private boolean isDeleteShown;	// 删除按钮是否正在显示

//         private ViewGroup mPointChild;	// 当前处理的item
  //       private LinearLayout.LayoutParams mLayoutParams;	// 当前处理的item的LayoutParams
        public ListItemViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.container_inner_item);
            Context context = itemView.getContext();
 //           WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
 //           DisplayMetrics dm = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(dm);
//            mScreenWidth = dm.widthPixels;
//            view.setOnClickListener(this);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            dateTime = (TextView) itemView.findViewById(R.id.txt_date_time);
            icon = (ImageView) itemView.findViewById(R.id.image_noice_icon);
            mItemLayout = (RelativeLayout) itemView.findViewById(R.id.ListItemLayout);
            //mItemLayout.setOnClickListener(this);
            mSwipeMenuLayout = (LinearLayout) itemView.findViewById(R.id.SwipeMenuLayout);
            ContainerLayout = (LinearLayout) itemView.findViewById(R.id.container_inner_item);
//            mLayoutParams = (LinearLayout.LayoutParams)ContainerLayout.getLayoutParams();
  //          mLayoutParams.width = mScreenWidth;
            itemView.findViewById(R.id.SwipeButtonDelete).setOnClickListener(this);
            ContainerLayout.setOnClickListener(this);  //TAKE HEED OF THAT EVEN THE BACKGROUND COLOR OF THE CHILD VIEW WILL COVER THE FATHER VIEW, THE CLICK EVENT WILL BE CAPTURED FIRST BY FATHER
        }
         @Override
         public void onClick(View view) {
            // if (onClickListener != null)
             if (view.getId() == R.id.container_inner_item) {
                 int pos = getAdapterPosition();
                 label.setTextColor(Color.GRAY);
                 //onClickListener.OnItemClick(getAdapterPosition());
          /*       if (pos>=0)
                     onClickListener.OnItemClick(items.get(pos),pos);
                 else
                 {
                     notifyDataSetChanged();
                     pos = getAdapterPosition();
                     onClickListener.OnItemClick(items.get(pos),pos);
                 }*/
                 onClickListener.OnItemClick(items.get(pos),pos);
             }
             if (view.getId() == R.id.SwipeButtonDelete)
             {
                 onClickListener.OnDeleteBtnClick(getAdapterPosition());
             }
             if (view.getId() == R.id.SwipeButtonFav)
             {
                 onClickListener.OnFavoriteBtnClick(getAdapterPosition());
             }
         }
         public TextView getLabel() {
             return label;
         }

         public TextView getDateTime() {
             return dateTime;
         }

         public ImageView getIcon() {
             return icon;
         }

         public CommonItemForList getItemForList() {
             return itemForList;
         }

         public View getView() {
             return view;
         }

         public RelativeLayout getmItemLayout() {
             return mItemLayout;
         }

         public LinearLayout getmSwipeMenuLayout() {
             return mSwipeMenuLayout;
         }

         public LinearLayout getContainerLayout() {
             return ContainerLayout;
         }
     }
    public void RemoveData(int position)
    {
        if (garbageCollector!=null)
        {
            garbageCollector.OnDataRemoved(items.get(position),position);
        }
    }
    public void Update(int p)
    {
        items.clear();
        items = provider.getDataList();
    }
     public interface OnClickListener
     {
         void OnItemClick(CommonItemForList itemForList, int pos);
         void OnItemClick(int pos);
         void OnDeleteBtnClick(int pos);
         void OnFavoriteBtnClick(int pos);
     }
    public interface GarbageCollector
    {
        void OnDataRemoved(CommonItemForList itemForList, int position);
    }

}
