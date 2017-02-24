package kjw.kr.bunobuno.bunos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kjw.kr.bunobuno.R;

/**
 * Created by kjwook on 2017. 2. 20..
 */

public class BunoExpandableListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<BunoItem> bunoList;
    private BunoFragment.BunoItemListener listener;

    public BunoExpandableListAdapter(List<BunoItem> data, BunoFragment.BunoItemListener listener) {
        this.bunoList = data;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        Context context = parent.getContext();

        switch(viewType) {
            case HEADER : {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_header, parent, false);

                ListHeaderViewHolder header = new ListHeaderViewHolder(view);

                return header;
            }
            case CHILD: {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);

                return child;
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final BunoItem data = bunoList.get(position);

        switch (data.type) {
            case HEADER: {
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.data = data;
                itemController.headerTv.setText(data.headerTitle);
                if (data.invisibleChildren == null) {
                    itemController.toggleIv.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.toggleIv.setImageResource(R.drawable.circle_plus);
                }

                itemController.toggleIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.invisibleChildren == null) {
                            data.invisibleChildren = new ArrayList<>();
                            int count = 0;
                            int pos = bunoList.indexOf(itemController.data);

                            while (bunoList.size() > pos + 1 && bunoList.get(pos + 1).type == CHILD) {
                                data.invisibleChildren.add(bunoList.remove(pos + 1));
                                count++;
                            }

                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.toggleIv.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = bunoList.indexOf(itemController.data);
                            int index = pos + 1;
                            for (BunoItem i : data.invisibleChildren) {
                                bunoList.add(index, i);
                                index++;
                            }

                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.toggleIv.setImageResource(R.drawable.circle_minus);
                            data.invisibleChildren = null;

                        }
                    }
                });
            }
                break;
            case CHILD: {
                final ListChildViewHolder itemController = (ListChildViewHolder) holder;
                itemController.childTv.setText(data.childTitle);
                itemController.childTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onSiteClick(data);
                    }
                });
            }
            break;
        }

    }

    @Override
    public int getItemCount() {
        return bunoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return bunoList.get(position).type;
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView headerTv;
        public ImageView toggleIv;
        public BunoItem data;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);

            headerTv = (TextView) itemView.findViewById(R.id.list_header_title);
            toggleIv = (ImageView) itemView.findViewById(R.id.list_header_btn_expand_toggle);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {

        public TextView childTv;
        public ImageView kakaoIv;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            childTv = (TextView) itemView.findViewById(R.id.list_child_tv);
            kakaoIv = (ImageView) itemView.findViewById(R.id.list_child_kakao_iv);

        }
    }

    public static class BunoItem {

        private int type;
        private String childTitle;
        private String headerTitle;
        private String id;

        public List<BunoItem> invisibleChildren;

        public BunoItem() {

        }

        public BunoItem(int type, String headerTitle, String childTitle, String id) {
            this.type = type;
            this.childTitle = childTitle;
            this.headerTitle = headerTitle;
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getChildTitle() {
            return childTitle;
        }

        public void setChildTitle(String childTitle) {
            this.childTitle = childTitle;
        }

        public String getHeaderTitle() {
            return headerTitle;
        }

        public void setHeaderTitle(String headerTitle) {
            this.headerTitle = headerTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<BunoItem> getInvisibleChildren() {
            return invisibleChildren;
        }

        public void setInvisibleChildren(List<BunoItem> invisibleChildren) {
            this.invisibleChildren = invisibleChildren;
        }
    }

}
