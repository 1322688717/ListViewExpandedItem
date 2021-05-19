package com.example.listviewexpandeditem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //声明变量
    private ListView lv_one;
    private int mExpandedMenuPos = -1;
    private Adapter_one adapter;
    private List<Integer> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //关联控件并且给控件设置适配器个点击事件
        lv_one = findViewById(R.id.lv_one);
        lv_one.setAdapter(adapter = new Adapter_one(this,list));
        lv_one.setOnItemClickListener(new Onitemclick());
        //添加数据的方法
        init();
    }
    /**
     * 设置自定义适配器
     */
    public class Adapter_one extends BaseAdapter {
        final LayoutInflater mLayoutInflater;
        final List<Integer> list;
        /**
         * 适配器的构造方法
         * @param context
         * @param list
         */
        public Adapter_one(Context context, List<Integer> list) {
            mLayoutInflater = LayoutInflater.from(context);
            this.list = list;
        }
        /**
         * 内部类ViewHolder，这是个容器用来存放展开的view
         */
        private class ViewHolder {
            public View root;
            public TextView txt;
            //单独的linerlayout
            public View menu;
            public ViewHolder(View viewRoot) {
                root = viewRoot;
                txt = viewRoot.findViewById(R.id.tv_yangpin);
                menu = viewRoot.findViewById(R.id.listview_menu_item_menu);
            }
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                //设置view
                convertView = mLayoutInflater.inflate(R.layout.item, parent, false);
                //给视图view设置一个tag
                convertView.setTag(new ViewHolder(convertView));
            }
            if (convertView != null && convertView.getTag() instanceof ViewHolder) {
                final ViewHolder holder = (ViewHolder)convertView.getTag();
                holder.txt.setText(String.valueOf(getItem(position)));
                holder.menu.setVisibility(position == mExpandedMenuPos ? View.VISIBLE : View.GONE);
            }
            return convertView;
        }
        /**
         * 对listview里面item中的控件设置点击事件
         */
        private OnMenuClickListenser mOnMenuClickListenser = new OnMenuClickListenser();
        private class OnMenuClickListenser implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                mExpandedMenuPos = -1;
                notifyDataSetChanged();
            }
        }
    }
    //将数字0-5当作数据传给list
    private void init() {
        for (int i = 0; i <= 5; i++){
            list.add(i);
        }
    }
    /**
     * listview中，对item设置点击监听
     */
    public class Onitemclick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == mExpandedMenuPos) {
                mExpandedMenuPos = -1;
            } else {
                mExpandedMenuPos = position;
            }
           adapter.notifyDataSetChanged();
        }
    }
}